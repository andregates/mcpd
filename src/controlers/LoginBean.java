package controlers;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import models.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {

	private Usuario usuarioLogado;

	public Usuario getUsuarioLogado() {

		if (usuarioLogado == null)
			usuarioLogado = new Usuario();

		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public String autenticar(){
		
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			usuarioLogado = usuarioDAO.autenticar(usuarioLogado.getNomeUsuario(), usuarioLogado.getCpf());
			
			if (usuarioLogado == null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten��o! Login ou Senha inv�lidos", ""));
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			} else {
				return "/template/home.xhtml?faces-redirect=true";
			}
			
		} catch (RuntimeException e) {
			// TODO: handle exception
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten��o! E", "Erro interno" + e.getMessage()));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}
		
		//TODO SAIR
	}
	

}
