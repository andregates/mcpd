package controlers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.RegistroDAO;
import models.Registro;

@SuppressWarnings("serial")
@ManagedBean
//Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@ViewScoped
public class RegistroBean implements Serializable{

	private Registro registro;
	private List<Registro> registros;
	
	public Registro getRegistro() {
		return registro;
	}
	
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}
	
	public List<Registro> getRegistros() {
		return registros;
	}
	
	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
	
	 //Método para limpar o campo.
    //Chamar na view, quando o usuário clicar no botão.
    public void novo(){
        registro = new Registro();
    }
	
	public void salvar(){
        try{
                RegistroDAO registroDAO = new RegistroDAO();
                registroDAO.salvar(registro);

                novo();

                Messages.addGlobalInfo("Cadastro realizado com sucesso");

        }catch(RuntimeException erro){
            Messages.addGlobalError("Erro ao cadastrar");
            erro.printStackTrace();
        }
    }
    
    @PostConstruct //m�todo construtor para chamar automaticamente o metodo quando o ManagedBean for criado
    public void listar(){
        try {
           RegistroDAO culturaDAO = new RegistroDAO();
            registros = culturaDAO.listar("Registro");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Erro ao listar");
            erro.printStackTrace();
        }
    }
    
    public void editar(ActionEvent evento){
        registro = (Registro) evento.getComponent().getAttributes().get("registroSelecionado");
		
    }
    
    public void excluir(ActionEvent evento){
		
        try {
            //pega o coponente do evento, pega os atributos do componente, pega pelo nome do aributo.
            registro = (Registro) evento.getComponent().getAttributes().get("registroSelecionado");

            RegistroDAO registroDAO = new RegistroDAO();
            registroDAO.deletar(registro);

            registros = registroDAO.listar("Registro");

            Messages.addGlobalInfo("Cadastro removido com sucesso");

        } catch (RuntimeException erro) {
            Messages.addGlobalError("Erro ao tentar remover");
            erro.printStackTrace();
        }
    }
}
