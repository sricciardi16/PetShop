package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Elemento;
import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class OrdineDAO implements DAO<Ordine> {

    private DataSource dataSource;
    private static final String TABLE_NAME = "ordine";

    public OrdineDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public synchronized void create(Ordine ordine) throws PetShopException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (prezzo, id_utente, id_metodo_pagamento, id_metodo_spedizione, id_indirizzo) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setDouble(1, ordine.getPrezzo());
            preparedStatement.setInt(2, ordine.getIdUtente());
            preparedStatement.setInt(3, ordine.getIdMetodoPagamento());
            preparedStatement.setInt(4, ordine.getIdMetodoSpedizione());
            preparedStatement.setInt(5, ordine.getIdIndirizzo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PetShopException("Errore durante la creazione dell'ordine", 500, e);
        }
    }
    
    
    public synchronized int createAndGetId(Ordine ordine) throws PetShopException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (prezzo, id_utente, id_metodo_pagamento, id_metodo_spedizione, id_indirizzo) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1; // Valore predefinito nel caso in cui l'ID non venga generato correttamente

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1, ordine.getPrezzo());
            preparedStatement.setInt(2, ordine.getIdUtente());
            preparedStatement.setInt(3, ordine.getIdMetodoPagamento());
            preparedStatement.setInt(4, ordine.getIdMetodoSpedizione());
            preparedStatement.setInt(5, ordine.getIdIndirizzo());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante la creazione dell'ordine", 500, e);
        }

        return generatedId;
    }


    @Override
    public synchronized boolean delete(int id) throws PetShopException {
        int result = 0;
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PetShopException("Errore durante l'eliminazione dell'ordine", 500, e);
        }

        return result != 0;
    }

    @Override
    public synchronized List<Ordine> retrieveAll(String order) throws PetShopException {
        List<Ordine> ordini = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Ordine bean = new Ordine();
                bean.setId(rs.getInt("id"));
                bean.setDataOra(rs.getTimestamp("data_ora"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setStato(rs.getString("stato"));
                bean.setIdUtente(rs.getInt("id_utente"));
                bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
                bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
                bean.setIdIndirizzo(rs.getInt("id_indirizzo"));

                ordini.add(bean);
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero degli ordini", 500, e);
        }

        return ordini;
    }

    public synchronized List<Ordine> retrieveAll() throws PetShopException {
        return retrieveAll("");
    }

    @Override
    public synchronized Ordine retrieveByKey(int id) throws PetShopException {
        Ordine bean = new Ordine();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setId(rs.getInt("id"));
                    bean.setDataOra(rs.getTimestamp("data_ora"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setStato(rs.getString("stato"));
                    bean.setIdUtente(rs.getInt("id_utente"));
                    bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
                    bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
                    bean.setIdIndirizzo(rs.getInt("id_indirizzo"));
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero dell'ordine", 500, e);
        }

        return bean;
    }
    
    public List<Ordine> retrieveByUtente(Utente utente) throws PetShopException {
        List<Ordine> ordini = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, utente.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Ordine bean = new Ordine();
                    bean.setId(rs.getInt("id"));
                    bean.setDataOra(rs.getTimestamp("data_ora"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setStato(rs.getString("stato"));
                    bean.setIdUtente(rs.getInt("id_utente"));
                    bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
                    bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
                    bean.setIdIndirizzo(rs.getInt("id_indirizzo"));

                    ordini.add(bean);
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero degli ordini dell'utente", 500, e);
        }

        return ordini;
    }
    
    public synchronized List<Elemento> getElementiOrdine(int idOrdine) throws PetShopException {
        List<Elemento> elementi = new ArrayList<>();
        String selectSQL = "SELECT * FROM elemento WHERE id_ordine = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, idOrdine);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Elemento bean = new Elemento();
                    bean.setId(rs.getInt("id"));
                    bean.setNome(rs.getString("nome"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getDouble("prezzo"));
                    bean.setImmagine(rs.getString("immagine"));
                    bean.setQuantita(rs.getInt("quantita"));
                    bean.setIdProdotto(rs.getInt("id_prodotto"));
                    bean.setIdOrdine(rs.getInt("id_ordine"));

                    elementi.add(bean);
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero degli elementi dell'ordine", 500, e);
        }

        return elementi;
    }
}
