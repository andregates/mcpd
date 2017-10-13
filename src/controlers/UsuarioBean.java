package controlers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import models.Usuario;
import org.omnifaces.util.Messages;
import dao.UsuarioDAO;

/**
 *
 * @author larys
 */
@ManagedBean
// Os objetos s� ficam "vivos" enquanto existir a sess�o.
@SessionScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// M�todo para limpar o campo.
	// Chamar l� na view, quando o usu�rio clicar no bot�o.
	public void novo() {
		usuario = new Usuario();
	}

	public String salvar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			Date date = new Date();

			this.usuario.setDataAtivacao(date);
			usuarioDAO.salvar(this.usuario);

			novo();

			// Flash scope
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));

			// Essa parte faz com que a mensagem de sucesso seja perpetuada apos o
			// redirecionamento
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();

			return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";

		}

		return "/usuarios/usuarios.xhtml?faces-redirect=true";

	}

	@PostConstruct // m�todo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public String editar(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionada");
		return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";
		
	}

	public void excluir(Usuario usuario) {

		try {
			// pega o coponente do evento, pega os atributos do componente, pega pelo nome
			// do aributo.
			//usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionada");

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.deletar(usuario);

			usuarios = usuarioDAO.listar();

			Messages.addGlobalInfo("Cadastro removido com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar remover");
			erro.printStackTrace();
		}
	}
}
