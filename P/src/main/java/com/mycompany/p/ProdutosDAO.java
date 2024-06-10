import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultSet;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto){
        conn = new ConectaDAO().connectDB();

        try {
            String query = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(query);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new ConectaDAO().connectDB();

        try {
            String query = "SELECT * FROM produtos";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();

            while(resultSet.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        }

        return listagem;
    }

    public void venderProduto(int id) {
        conn = new ConectaDAO().connectDB();

        try {
            String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new ConectaDAO().connectDB();

        try {
            String query = "SELECT * FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();

            ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                vendidos.add(produto);
            }
            return vendidos;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + erro.getMessage());
        }

        return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
    }
}