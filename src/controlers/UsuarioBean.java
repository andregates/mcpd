package controlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Usuario;
import org.omnifaces.util.Messages;
import dao.UsuarioDAO;

/**
 *
 * @author larys Edit by Gabriel Quaresma
 */
@ManagedBean
// Os objetos sï¿½ ficam "vivos" enquanto existir a sessï¿½o.
@SessionScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;

	public UsuarioBean() {
		this.usuario = new Usuario();
		this.usuarios = new ArrayList<Usuario>();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar("Usuario");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// Mï¿½todo para limpar o campo.
	// Chamar lï¿½ na view, quando o usuï¿½rio clicar no botï¿½o.
	public void novo() {
		usuario = new Usuario();
	}
	
	public String moveToCadastroUser() {
		novo();
		return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";
	}

	public boolean valida() {
		if (usuario.getNomeUsuario().isEmpty() || usuario.getNomeCompleto().isEmpty() || usuario.getCpf().isEmpty()
				|| usuario.getCpf().length() > 11) {
			return true;
		} else {
			return false;
		}
	}

	public String salvar() {
		// Flash scope
		// faz com que a mensagem de sucesso seja perpetuada apos o
		// redirecionamento

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			if (usuario.getUsuarioId() == null) {

				if (valida() == true) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";
				} else {
					usuarioDAO.salvar(this.usuario);
					Date date = new Date();
					this.usuario.setDataAtivacao(date);
					usuario = new Usuario();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cadastro realizado com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/usuarios/usuarios.xhtml?faces-redirect=true";
				}
			} else {

				if (valida() == true) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";
				} else {
					usuarioDAO.update(this.usuario);
					usuario = new Usuario();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Edição realizada com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/usuarios/usuarios.xhtml?faces-redirect=true";
				}
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();

			return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";

		}
	}

	@PostConstruct // mï¿½todo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			if (usuario.getDataInativacao() != null)
				usuarios = usuarioDAO.listar("Usuario");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public String atualizar(Usuario usuario) {
		usuario.getUsuarioId();
		this.usuario = usuario;
		return "/usuarios/cadastrar_usuarios.xhtml?faces-redirect=true";
	}

	public void excluir(Usuario usuario) {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage messagem = new FacesMessage("Usuário removido");
		messagem.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, messagem);
		usuario.setDataInativacao(Calendar.getInstance().getTime());
		usuarioDao.update(usuario);
		this.usuario = new Usuario();
		this.usuarios = usuarioDao.listar("Usuario");
	}
}
