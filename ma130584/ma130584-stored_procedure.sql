USE [ma130584]
GO
/****** Object:  StoredProcedure [dbo].[SP_FINAL_PRICE]    Script Date: 22.6.2019. 10.33.48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[SP_FINAL_PRICE]
@idPorudzbina int,
@ukupnaCena float output
as
begin
	declare @myCursor Cursor
	declare @idStavka int
	declare @popust int
	declare @datum int
	declare @dodatniPopust int
	declare @pom int

	set @myCursor = Cursor for
	select idStavka from Sadrzi where IdPorudzbina = @idPorudzbina

	open @myCursor
	
	set @ukupnaCena = 0
	select @datum = count(idPorudzbina) from Porudzbina where IdKupac = (select idKupac from Porudzbina where IdPorudzbina = @idPorudzbina) and datediff(dd, DatumPorudzbine, GETDATE()) < 30 and IdPorudzbina != @idPorudzbina and UkupnaCena > 10000 and StanjePorudzbine = 'sent' or StanjePorudzbine = 'arrived' 
	if (@datum = 0)
	begin
		set @dodatniPopust = 0
	end
	else
	begin
		set @dodatniPopust = 2
	end
	fetch next from @myCursor
	into @idStavka
	while @@FETCH_STATUS = 0
	begin
		select @popust = popust from Prodavnica where idProdavnica = (select idProdavnica from Stavka where idStavka = @idStavka)

		set @popust = @popust + @dodatniPopust

		select @pom = cena from Stavka where idStavka = @idStavka

		set @ukupnaCena = @ukupnaCena * 1.0 + @pom * 1.0 - @pom * 1.0 / 100 * @popust 

		fetch next from @myCursor
		into @idStavka
	end
	close @myCursor
	deallocate @myCursor

end





USE [ma130584]
GO
/****** Object:  StoredProcedure [dbo].[spObrisiPodatke]    Script Date: 22.6.2019. 10.34.05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER proc [dbo].[spObrisiPodatke]
as
begin

delete from Sadrzi
dbcc checkident(Sadrzi, reseed, 0)

delete from TransakcijaProdavnica
dbcc checkident(TransakcijaProdavnica, reseed, 0)

delete from Porudzbina
dbcc checkident(Porudzbina, reseed, 0)

delete from Transakcija
dbcc checkident(Transakcija, reseed, 0)

delete from Stavka
dbcc checkident(Stavka, reseed, 0)

delete from Linija
dbcc checkident(Linija, reseed, 0)

delete from Oglas
dbcc checkident(Oglas, reseed, 0)

delete from Kupac
dbcc checkident(Kupac, reseed, 0)

delete from Artikal
dbcc checkident(Artikal, reseed, 0)

delete from Prodavnica
dbcc checkident(Prodavnica, reseed, 0)

delete from Grad
dbcc checkident(Grad, reseed, 0)

end
