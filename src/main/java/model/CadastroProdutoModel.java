/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 232.004390
 */
public class CadastroProdutoModel {
    
    private String codigoBarras;
    private String nomeProduto;
    private String fabricante;
    private String marca;
    private String dataFabricacao;
    private String dataVencimento;
    private long quantidade;
    private String valor;
    private String total;
    private String status;

    public String getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    } 
    
    public CadastroProdutoModel () {
    }

    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getFabricante() {
        return this.fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDataFabricacao() {
        return this.dataFabricacao;
    }

    public void setDataFabricacao(String dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    public String getStatus () {
        return this.status;
    }
    
    public void setStatus (String status) {
        this.status = status;
    }
}
