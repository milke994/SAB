
CREATE TABLE [Artikal]
( 
	[IdArtikal]          integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL 
)
go

ALTER TABLE [Artikal]
	ADD CONSTRAINT [XPKArtikal] PRIMARY KEY  CLUSTERED ([IdArtikal] ASC)
go

CREATE TABLE [Grad]
( 
	[IdGrad]             integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL 
)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IdGrad] ASC)
go

CREATE TABLE [Kupac]
( 
	[IdKupac]            integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL ,
	[Iznos]              decimal(10,3)  NULL ,
	[IdGrad]             integer  NULL 
)
go

ALTER TABLE [Kupac]
	ADD CONSTRAINT [XPKKupac] PRIMARY KEY  CLUSTERED ([IdKupac] ASC)
go

CREATE TABLE [Linija]
( 
	[IdGrad1]            integer  NOT NULL ,
	[IdGrad2]            integer  NOT NULL ,
	[Daljina]            integer  NULL ,
	[IdLinija]           integer  NOT NULL  IDENTITY ( 1,1 ) 
)
go

ALTER TABLE [Linija]
	ADD CONSTRAINT [XPKLinija] PRIMARY KEY  CLUSTERED ([IdLinija] ASC,[IdGrad1] ASC,[IdGrad2] ASC)
go

CREATE TABLE [Oglas]
( 
	[IdProdavnica]       integer  NOT NULL ,
	[IdArtikal]          integer  NOT NULL ,
	[Cena]               decimal(10,3)  NULL ,
	[Stanje]             integer  NULL ,
	[IdOglas]            integer  NOT NULL  IDENTITY ( 1,1 ) 
)
go

ALTER TABLE [Oglas]
	ADD CONSTRAINT [XPKOglas] PRIMARY KEY  CLUSTERED ([IdOglas] ASC,[IdProdavnica] ASC,[IdArtikal] ASC)
go

CREATE TABLE [Porudzbina]
( 
	[IdPorudzbina]       integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[IdKupac]            integer  NOT NULL ,
	[UkupnaCena]         decimal(10,3)  NULL ,
	[DatumPorudzbine]    datetime  NULL ,
	[StanjePorudzbine]   varchar(100)  NULL 
	CONSTRAINT [ogranicenje]
		CHECK  ( [StanjePorudzbine]='created' OR [StanjePorudzbine]='sent' OR [StanjePorudzbine]='arrived' ),
	[DatumStizanja]      datetime  NULL ,
	[IdGrad]             integer  NULL 
)
go

ALTER TABLE [Porudzbina]
	ADD CONSTRAINT [XPKPorudzbina] PRIMARY KEY  CLUSTERED ([IdPorudzbina] ASC,[IdKupac] ASC)
go

CREATE TABLE [Prodavnica]
( 
	[IdProdavnica]       integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(100)  NULL ,
	[IdGrad]             integer  NULL ,
	[Popust]             integer  NULL ,
	[Iznos]              decimal(10,3)  NULL 
)
go

ALTER TABLE [Prodavnica]
	ADD CONSTRAINT [XPKProdavnica] PRIMARY KEY  CLUSTERED ([IdProdavnica] ASC)
go

CREATE TABLE [Sadrzi]
( 
	[IdSadrzi]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[IdPorudzbina]       integer  NOT NULL ,
	[IdKupac]            integer  NOT NULL ,
	[IdStavka]           integer  NULL ,
	[IdProdavnica]       integer  NULL ,
	[IdArtikal]          integer  NULL ,
	[IdOglas]            integer  NULL 
)
go

ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [XPKSadrzi] PRIMARY KEY  CLUSTERED ([IdSadrzi] ASC,[IdPorudzbina] ASC,[IdKupac] ASC)
go

CREATE TABLE [Stavka]
( 
	[IdProdavnica]       integer  NOT NULL ,
	[IdArtikal]          integer  NOT NULL ,
	[Kolicina]           integer  NULL ,
	[Cena]               decimal(10,3)  NULL ,
	[IdStavka]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[IdOglas]            integer  NOT NULL 
)
go

ALTER TABLE [Stavka]
	ADD CONSTRAINT [XPKStavka] PRIMARY KEY  CLUSTERED ([IdStavka] ASC,[IdProdavnica] ASC,[IdArtikal] ASC,[IdOglas] ASC)
go

CREATE TABLE [Transakcija]
( 
	[IdTransakcija]      integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[IdStavka]           integer  NOT NULL ,
	[IdProdavnica]       integer  NOT NULL ,
	[IdArtikal]          integer  NOT NULL ,
	[IdKupac]            integer  NOT NULL ,
	[Iznos]              decimal(10,3)  NULL ,
	[IdOglas]            integer  NOT NULL ,
	[datum]              datetime  NULL 
)
go

ALTER TABLE [Transakcija]
	ADD CONSTRAINT [XPKTransakcija] PRIMARY KEY  CLUSTERED ([IdTransakcija] ASC,[IdStavka] ASC,[IdProdavnica] ASC,[IdArtikal] ASC,[IdKupac] ASC,[IdOglas] ASC)
go

CREATE TABLE [TransakcijaProdavnica]
( 
	[IdProdavnica]       integer  NOT NULL ,
	[IdPorudzbina]       integer  NOT NULL ,
	[IdKupac]            integer  NOT NULL ,
	[Iznos]              decimal(10,3)  NULL ,
	[ProfitSistema]      decimal(10,3)  NULL ,
	[IdTransakcijaProdavnica] integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[datum]              datetime  NULL 
)
go

ALTER TABLE [TransakcijaProdavnica]
	ADD CONSTRAINT [XPKTransakcijaProdavnica] PRIMARY KEY  CLUSTERED ([IdTransakcijaProdavnica] ASC,[IdProdavnica] ASC,[IdPorudzbina] ASC,[IdKupac] ASC)
go


ALTER TABLE [Kupac]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([IdGrad]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Linija]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([IdGrad1]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Linija]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([IdGrad2]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Oglas]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([IdProdavnica]) REFERENCES [Prodavnica]([IdProdavnica])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Oglas]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([IdArtikal]) REFERENCES [Artikal]([IdArtikal])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Porudzbina]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([IdKupac]) REFERENCES [Kupac]([IdKupac])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Porudzbina]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([IdGrad]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Prodavnica]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([IdGrad]) REFERENCES [Grad]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([IdPorudzbina],[IdKupac]) REFERENCES [Porudzbina]([IdPorudzbina],[IdKupac])
		ON UPDATE CASCADE
go

ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([IdStavka],[IdProdavnica],[IdArtikal],[IdOglas]) REFERENCES [Stavka]([IdStavka],[IdProdavnica],[IdArtikal],[IdOglas])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Stavka]
	ADD CONSTRAINT [R_4] FOREIGN KEY ([IdOglas],[IdProdavnica],[IdArtikal]) REFERENCES [Oglas]([IdOglas],[IdProdavnica],[IdArtikal])
		ON UPDATE CASCADE
go


ALTER TABLE [Transakcija]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([IdStavka],[IdProdavnica],[IdArtikal],[IdOglas]) REFERENCES [Stavka]([IdStavka],[IdProdavnica],[IdArtikal],[IdOglas])
		ON UPDATE CASCADE
go

ALTER TABLE [Transakcija]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([IdKupac]) REFERENCES [Kupac]([IdKupac])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [TransakcijaProdavnica]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([IdProdavnica]) REFERENCES [Prodavnica]([IdProdavnica])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [TransakcijaProdavnica]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([IdPorudzbina],[IdKupac]) REFERENCES [Porudzbina]([IdPorudzbina],[IdKupac])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go




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
