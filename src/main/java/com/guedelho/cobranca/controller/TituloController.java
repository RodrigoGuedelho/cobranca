package com.guedelho.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guedelho.cobranca.model.StatusTitulo;
import com.guedelho.cobranca.model.Titulo;
import com.guedelho.cobranca.repository.Titulos;

import javassist.compiler.ast.ASTList;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	private final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private Titulos titulos;
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(titulo);

		return mv;
	}
	@RequestMapping
	public ModelAndView pesquisar(){
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("listaTitulos",  titulos.findAll());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes redirectAttributes) {
		if(errors.hasErrors())
			return CADASTRO_VIEW;	
		String mensagem = "Título salvo com sucesso.";
		if(titulo.getCodigo() != null) 
			mensagem =  "Título editado com sucesso.";
		
		titulos.save(titulo);
		redirectAttributes.addFlashAttribute("mensagem", mensagem);
		return "redirect:/titulos/novo";
	}
	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes redirectAttributes) {
		titulos.deleteById(codigo);
		redirectAttributes.addFlashAttribute("mensagem", "Título excluído com sucesso.");
		return "redirect:/titulos";
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	

}
