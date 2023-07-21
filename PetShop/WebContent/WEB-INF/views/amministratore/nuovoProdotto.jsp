<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Crea un nuovo prodotto</title>
</head>
<body>
	<jsp:include page="../fragments/adminHeader.jsp" />
    <form action="${pageContext.request.contextPath}/admin/prodotti/create" method="POST" enctype="multipart/form-data">
        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" required><br>
        <label for="descrizione">Descrizione:</label><br>
        <input type="text" id="descrizione" name="descrizione" required><br>
        <label for="prezzo">Prezzo:</label><br>
        <input type="number" id="prezzo" name="prezzo" step="0.01" required><br>
        <label for="inMagazzino">In Magazzino:</label><br>
        <input type="number" id="inMagazzino" name="inMagazzino" required><br>
        <label for="animale">Animale:</label><br>
        <select id="animale" name="animale" required>
            <option value="" disabled selected>Seleziona</option>
            <option value="cane">Cane</option>
            <option value="gatto">Gatto</option>
            <!-- Aggiungi qui altre opzioni se necessario -->
        </select><br>
        <label for="tipologia">Tipologia:</label><br>
        <select id="tipologia" name="tipologia" required>
            <option value="" disabled selected>Seleziona</option>
        </select><br>
        <label for="tipologiaIn">Sotto Tipologia:</label><br>
        <select id="tipologiaIn" name="tipologiaIn" required>
            <option value="seleziona" disabled selected>Seleziona</option>
        </select><br>
        <label for="immagine">Immagine:</label><br>
        <input type="file" id="immagine" name="immagine" required><br>
        <input type="submit" value="Crea prodotto">
    </form>
    <jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/async/nuovoProdottoCategoriaAsync.js"></script>

</body>
</html>
