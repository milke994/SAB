USE [ma130584]
GO
/****** Object:  Trigger [dbo].[TR_TRANSFER_MONEY_TO_SHOPS]    Script Date: 22.6.2019. 10.32.37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER trigger [dbo].[TR_TRANSFER_MONEY_TO_SHOPS]
on [dbo].[Porudzbina]
for update
as
begin
	declare @stanje varchar(30)
	declare @idPorudzbina int
	declare @myCursor Cursor
	declare @idStavka int
	declare @datum int
	declare @profitSistem int
	declare @dodatniPopust int
	declare @idProdavnica int
	declare @idArtikal int
	declare @idKupac int
	declare @idOglas int
	declare @iznos float
	declare @trenutnaCena float
	declare @popust int
	declare @profitSistema float
	declare @profitProdavnice float
	declare @vremeStiglo date
	

	select @stanje = StanjePorudzbine, @idPorudzbina = IdPorudzbina from inserted

	select @idKupac = idKupac from Porudzbina where IdPorudzbina = @idPorudzbina

	select @datum = count(idPorudzbina) from Porudzbina where (IdKupac = (select idKupac from Porudzbina where IdPorudzbina = @idPorudzbina)) 
	and (datediff(dd, DatumPorudzbine, GETDATE()) < 30) and (IdPorudzbina != @idPorudzbina) and (UkupnaCena > 10000) and (StanjePorudzbine = 'sent' or StanjePorudzbine = 'arrived') 
	if (@datum = 0)
	begin
		set @profitSistem = 5
		set @dodatniPopust = 0
	end
	else
	begin
		set @profitSistem = 3
		set @dodatniPopust = 2
	end
	
	set @myCursor = Cursor for
	select idStavka from Sadrzi where IdPorudzbina = @idPorudzbina

	if(@stanje = 'sent')
	begin
		open @myCursor
		fetch next from @myCursor
		into @idStavka

		while @@FETCH_STATUS = 0
		begin
			select @vremeStiglo = DatumPorudzbine from Porudzbina where IdPorudzbina = @idPorudzbina

			select @idProdavnica = idProdavnica, @idArtikal = idArtikal, @idOglas = idOglas, @trenutnaCena = cena from Stavka where IdStavka = @idStavka

			select @popust = popust from Prodavnica where idProdavnica = @idProdavnica

			set @iznos = @trenutnaCena * 1.0 - @trenutnaCena * 1.0* (@popust + @dodatniPopust) / 100

			set @profitSistema = @iznos * 1.0 * @profitSistem / 100

			insert into Transakcija(IdStavka, IdProdavnica, IdArtikal, IdKupac, IdOglas, Iznos, datum) 
			values (@idStavka, @idProdavnica, @idArtikal, @idKupac, @idOglas, @iznos, @vremeStiglo)

			fetch next from @myCursor
			into @idStavka
		end
		close @myCursor
		deallocate @myCursor
	end

	if(@stanje = 'arrived')
	begin
		open @myCursor
		fetch next from @myCursor
		into @idStavka

		while @@FETCH_STATUS = 0
		begin
			select @vremeStiglo = DatumStizanja from Porudzbina where IdPorudzbina = @idPorudzbina

			select @idProdavnica = idProdavnica, @trenutnaCena = iznos from Transakcija where IdStavka = @idStavka

			select @popust = popust from Prodavnica where idProdavnica = @idProdavnica

			set @profitSistema = @trenutnaCena * 1.0 * @profitSistem / 100

			set @profitProdavnice = @trenutnaCena - @profitSistema

			insert into TransakcijaProdavnica(IdProdavnica, IdPorudzbina, IdKupac, Iznos, ProfitSistema, datum) 
			values (@idProdavnica,@idPorudzbina, @idKupac, @profitProdavnice, @profitSistema, @vremeStiglo)

			update Prodavnica set iznos = (select iznos from Prodavnica where IdProdavnica = @idProdavnica) + @profitProdavnice where IdProdavnica = @idProdavnica

			fetch next from @myCursor
			into @idStavka
		end
		close @myCursor
		deallocate @myCursor
	end
end