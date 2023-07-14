package it.petshop.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Indirizzo;
import it.petshop.utility.PetShopException;
import it.petshop.model.Utente;

public class IndirizzoDAO implements DAO<Indirizzo> {

    private DataSource dataSource;
    private static final String TABLE_NAME = "indirizzo";

    public IndirizzoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public synchronized void create(Indirizzo indirizzo) throws PetShopException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (alias, via, numero, citta, codice_postale, provincia, paese, id_utente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, indirizzo.getAlias());
            preparedStatement.setString(2, indirizzo.getVia());
            preparedStatement.setString(3, indirizzo.getNumero());
            preparedStatement.setString(4, indirizzo.getCitta());
            preparedStatement.setString(5, indirizzo.getCodicePostale());
            preparedStatement.setString(6, indirizzo.getProvincia());
            preparedStatement.setString(7, indirizzo.getPaese());
            preparedStatement.setInt(8, indirizzo.getIdUtente());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        	 System.out.println(e.getMessage());
            throw new PetShopException("Errore durante la creazione dell'indirizzo", e);
        }
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
            throw new PetShopException("Errore durante l'eliminazione dell'indirizzo", e);
        }

        return result != 0;
    }

    @Override
    public synchronized List<Indirizzo> retrieveAll(String order) throws PetShopException {
        List<Indirizzo> indirizzi = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Indirizzo bean = new Indirizzo();
                bean.setId(rs.getInt("id"));
                bean.setAlias(rs.getString("alias"));
                bean.setVia(rs.getString("via"));
                bean.setNumero(rs.getString("numero"));
                bean.setCitta(rs.getString("citta"));
                bean.setCodicePostale(rs.getString("codice_postale"));
                bean.setProvincia(rs.getString("provincia"));
                bean.setPaese(rs.getString("paese"));
                bean.setIdUtente(rs.getInt("id_utente"));

                indirizzi.add(bean);
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero degli indirizzi", e);
        }

        return indirizzi;
    }

    public synchronized List<Indirizzo> retrieveAll() throws PetShopException {
        return retrieveAll("");
    }

    @Override
    public synchronized Indirizzo retrieveByKey(int id) throws PetShopException {
        Indirizzo bean = new Indirizzo();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setId(rs.getInt("id"));
                    bean.setAlias(rs.getString("alias"));
                    bean.setVia(rs.getString("via"));
                    bean.setNumero(rs.getString("numero"));
                    bean.setCitta(rs.getString("citta"));
                    bean.setCodicePostale(rs.getString("codice_postale"));
                    bean.setProvincia(rs.getString("provincia"));
                    bean.setPaese(rs.getString("paese"));
                    bean.setIdUtente(rs.getInt("id_utente"));
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero dell'indirizzo", e);
        }

        return bean;
    }
    
    public synchronized List<Indirizzo> retrieveByUtente(Utente utente) throws PetShopException {
        List<Indirizzo> indirizzi = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ? ORDER BY id DESC";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, utente.getId());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Indirizzo bean = new Indirizzo();
                    bean.setId(rs.getInt("id"));
                    bean.setAlias(rs.getString("alias"));
                    bean.setVia(rs.getString("via"));
                    bean.setNumero(rs.getString("numero"));
                    bean.setCitta(rs.getString("citta"));
                    bean.setCodicePostale(rs.getString("codice_postale"));
                    bean.setProvincia(rs.getString("provincia"));
                    bean.setPaese(rs.getString("paese"));
                    bean.setIdUtente(rs.getInt("id_utente"));

                    indirizzi.add(bean);
                }
            }
        } catch (SQLException e) {
            throw new PetShopException("Errore durante il recupero degli indirizzi dell'utente", e);
        }

        return indirizzi;
    }
}
