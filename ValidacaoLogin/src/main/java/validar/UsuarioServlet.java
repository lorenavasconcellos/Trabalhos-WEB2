package validar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet(name="usuario.do", urlPatterns={"/usuario.do"})
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HashMap<String, String> usuarios = new HashMap<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String senha = request.getParameter("senha");
		Boolean resp = true;
		
		response.setContentType("text/html");
		
		if (nome == null || senha == null) {
			resp = false;
		}
		else if (nome.equals("") || senha.equals("")) {
			resp = false;
		}
		if (resp) {
			if (usuarios.containsKey(nome)) {
				String valida = usuarios.get(nome);
				if (senha.intern() == valida.intern()) {
					response.getWriter().print("<h1>Olá, " + nome + "!</h1>");
					}
				else {
					response.getWriter().print("Senha incorreta");
				}
			}
			else {
				System.out.println("Senha incorreta");
				request.getRequestDispatcher("./TelaCadastro.html").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String senha = request.getParameter("senha");
		Boolean resp = true;
		
		if (nome == null || senha == null) {
			resp = false;
		}
		if (nome.equals("") || senha.equals("")) {
			resp = false;
		}
		if (usuarios.containsKey(nome)) {
			resp = false;
		}
		if (resp) {
			Usuario user = new Usuario();
			
			user.setNome(nome);
			user.setSenha(senha);
			
			usuarios.put(nome, senha);
			response.getWriter().print("Cadastro efetuado com sucesso");
			request.getRequestDispatcher("./TelaLogin.html").forward(request, response);
		}
		else {
			response.getWriter().print("Não foi possível efetuar o cadastro");
		}
	}

}
