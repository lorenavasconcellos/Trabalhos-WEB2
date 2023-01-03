package Cadastrar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AlunoServlet
 */
@WebServlet(name="aluno.do", urlPatterns = {"/aluno.do"})
public class AlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlunoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String busca = request.getParameter("busca");
		Integer cod_erro = -1;
		Boolean ok = true;
		
		response.setContentType("text/html"); /*Sets the content type of the response being sent to the client*/
		
		if(busca != null) {
			if (busca.equals("todos")) {
				response.getWriter().print("<h3>Listando todos os alunos</h3>");
				for (Aluno aluno: alunos) {
					response.getWriter().print("Nome: " + aluno.getNome() + " Matrícula: " + aluno.getMatricula() + "<br/>");
				}
			}
			
			else {
				if (busca.equals("nome")) {
					String nomeBusca = request.getParameter("nome_busca");
					if (nomeBusca != null) {
						response.getWriter().print("<h3>Listando alunos por nome</h3>");
						Aluno aux = null;
						
						for (Aluno aluno: alunos) {
							if (nomeBusca.equals(aluno.getNome())) {
								aux = aluno;
								break;
							}
						}
						
						if (aux != null) {
							response.getWriter().print("Nome: " + aux.getNome() + " Matrícula: " + aux.getMatricula());
						}
						else {
							ok = false;
							cod_erro = 22; //aluno não encontrado 
						}
					}
					else {
						ok = false;
						cod_erro = 20; //não digitou nenhum nome
					}
				}
				else {
					if (busca.equals("matricula")) {
						String matBusca = request.getParameter("matricula_busca");
						
						if (matBusca != null) {
							String[] matriculas_lista = matBusca.split(",");
							response.getWriter().print("<h3>Listando alunos por matrícula</h3>");
							
							List<Aluno> aux_list_alunos = new ArrayList<Aluno>();
							
							for (int i = 0; i < matriculas_lista.length; i++) {
								int aux_mat = Integer.parseInt(matriculas_lista[i]);
								
								for (Aluno aluno: alunos) {
									if (aluno.getMatricula() == aux_mat) {
										aux_list_alunos.add(aluno);
										break;
									}
								}
							}
							
							if (aux_list_alunos.size() > 0) {
								for (Aluno aluno: aux_list_alunos) {
									response.getWriter().print("Nome: " + aluno.getNome() + " Matrícula: " + aluno.getMatricula());
								}
							}
						}	
						else {
							ok = false;
							cod_erro = 32;//escolheu matrícula para busca, mas não informou nenhuma
						}
					}
					else {
						ok = false;
						cod_erro = 40;//não existe essa opção de busca
					}
							
				}
			}
		}
		else {
			ok = false;
			cod_erro = 10;// busca nulo: informe uma opção válida para realização da busca
		}
		if (!ok) {//não ok, deu algum erro.
			
			String msg = "";
			if (cod_erro == 10)
				msg = "busca nulo: informe uma opção válida para realização da busca";
			if (cod_erro == 20) 
				msg = "busca por nome requer que um nome de aluno seja informado";
			if (cod_erro == 22)
				msg = "escolheu nome para busca, mas não informou nenhum";
			if (cod_erro == 32)
				msg = "escolheu matrícula para busca, mas não informou nenhuma";			
			if (cod_erro == 40)
				msg = "não existe essa opção de busca. ou pesquisa por tudo, ou filtra por nome ou por matrículas";

			response.getWriter().print(msg);
			
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String matricula = request.getParameter("matricula");
		Boolean resp = true;
		
		if (nome == null || matricula == null) {
			resp = false;
		}
		if (nome.equals("") || matricula.equals("")) {
			resp = false;
		}
		if (resp) {
			Aluno aluno = new Aluno();
			aluno.setNome(nome);
			aluno.setMatricula(Integer.parseInt(matricula));
			
			alunos.add(aluno);
			
			response.getWriter().print("Aluno inserido com sucesso!");
		}
		else {
			response.getWriter().print("Dados incorretos");
		}
	}

}
