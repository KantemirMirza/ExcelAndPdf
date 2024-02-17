package com.kani.excelandpdf.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PageRender <T>{
    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();
        this.numElementosPorPagina = 5;
        this.paginaActual = page.getTotalPages();
        this.paginaActual = page.getNumber() + 1;

        int desc, asc;
        if(totalPaginas <= numElementosPorPagina){
            desc = 1;
            asc = totalPaginas;
        }else{
            if(paginaActual <= numElementosPorPagina / 2){
                desc = 1;
                asc = numElementosPorPagina;
            } else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
                desc = totalPaginas - numElementosPorPagina + 1;
                asc = numElementosPorPagina;
            }else {
                desc = paginaActual - numElementosPorPagina / 2;
                asc = numElementosPorPagina;
            }
        }
        for(int i = 0; i < asc; i++){
            paginas.add(new PageItem(desc + i, paginaActual == desc + 1));
        }
    }

    public Boolean isFirst(){
        return page.isFirst();
    }

    public Boolean isLast(){
        return page.isLast();
    }

    public Boolean isHasNext(){
        return page.hasNext();
    }

    public Boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
