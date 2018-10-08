package com.senac.renan.exerciciomvc.controller;

import com.senac.renan.exerciciomvc.DAO.DAOProduto;
import com.senac.renan.exerciciomvc.model.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("mvc/produto")
public class ProdutoController {

    @GetMapping("/inclusao")
    public ModelAndView abrirFormulario() {
        return new ModelAndView("inclusao").addObject("produto", new Produto());
    }

    @GetMapping("/principal")
    public ModelAndView formulario() {
        return new ModelAndView("principal");
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(
            @ModelAttribute("produto") Produto produto,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("produto", produto);
        DAOProduto dao = new DAOProduto();
        try {
            dao.incluir(produto);
        } catch (Exception ex) {

        }
        return new ModelAndView("redirect:/mvc/produto/principal");
    }

    @GetMapping("/resultado")
    public ModelAndView mostrarResultado() {
        return new ModelAndView("resultado");
    }

}
