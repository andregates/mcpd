package controlers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.HistoricoDAO;
import models.Historico;

@SuppressWarnings("serial")
@ManagedBean(name="historicoBean")
//Os objetos so ficam "vivos" enquanto o usuario estiver na tela.
@ViewScoped
public class HistoricoBean {

	private Historico historico;
    private List<Historico> historicos;
    
	public Historico getHistorico() {
		return historico;
	}
	
	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	public List<Historico> getHistoricos() {
		return historicos;
	}
	
	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}
    
    public void novo() {
    	historico = new Historico();
    }
    
    public void salvar(){
        try{
                HistoricoDAO historicoDAO = new HistoricoDAO();
                historicoDAO.salvar(historico);

                novo();

                Messages.addGlobalInfo("Cadastro realizado com sucesso");

        }catch(RuntimeException erro){
            Messages.addGlobalError("Erro ao cadastrar");
            erro.printStackTrace();
        }
    }
    
    @PostConstruct //metodo construtor para chamar automaticamente o metodo quando o ManagedBean for criado
    public void listar(){
        try {
            HistoricoDAO historicoDAO = new HistoricoDAO();
            historicos = historicoDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Erro ao listar");
            erro.printStackTrace();
        }
    }
    
    public void editar(ActionEvent evento){
        historico = (Historico) evento.getComponent().getAttributes().get("historicoSelecionado");
		
    }
    
    public void excluir(ActionEvent evento){
		
        try {
            //pega o coponente do evento, pega os atributos do componente, pega pelo nome do aributo.
            historico = (Historico) evento.getComponent().getAttributes().get("historicoSelecionado");

            HistoricoDAO historicoDAO = new HistoricoDAO();
            historicoDAO.deletar(historico);

            historicos = historicoDAO.listar();

            Messages.addGlobalInfo("Cadastro removido com sucesso");

        } catch (RuntimeException erro) {
            Messages.addGlobalError("Erro ao tentar remover");
            erro.printStackTrace();
        }
    }
}
