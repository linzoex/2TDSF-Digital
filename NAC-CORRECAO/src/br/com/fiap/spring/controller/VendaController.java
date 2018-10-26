package br.com.fiap.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.spring.dao.VendaDAO;
import br.com.fiap.spring.exception.RegistroNaoEncontradoException;
import br.com.fiap.spring.model.Venda;

@Controller
@RequestMapping("venda")
public class VendaController {

	@Autowired
	private VendaDAO dao;
	
	@Transactional
	@PostMapping("remover")
	public String remover(int codigo, RedirectAttributes r) {
		try {
			dao.excluir(codigo);
		} catch (RegistroNaoEncontradoException e) {
			e.printStackTrace();
		}
		r.addFlashAttribute("msg", "Removido!");
		return "redirect:/venda/listar";
	}
	
	@GetMapping("buscar")
	public ModelAndView buscar(String nome) {
		return new ModelAndView("venda/lista")
				.addObject("vendas", dao.buscarPorCliente(nome));
	}
	
	@GetMapping("listar")
	public ModelAndView listar() {
		return new ModelAndView("venda/lista")
			.addObject("vendas", dao.listar());
	}
	
	@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable("id") int codigo) {
		return new ModelAndView("venda/edicao")
			.addObject("venda", dao.buscar(codigo));
	}
	
	@Transactional
	@PostMapping("editar")
	public String editar(Venda venda, RedirectAttributes r) {
		dao.atualizar(venda);
		r.addFlashAttribute("msg", "Atualizado!");
		return "redirect:/venda/listar";
	}
	
	
}
