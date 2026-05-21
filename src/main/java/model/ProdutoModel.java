/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 232.004390
 */
public class ProdutoModel {
    
    private int id;
    private String codigoBarras;
    private String nomeProduto;
    private String fabricante;
    private String marca;
    private String dataFabricacao;
    private long quantidade;
    private String valor;
    private String total;
    
    public ProdutoModel () {
    }
    
    public String getCodigoBarras () {
        return this.codigoBarras;
    }
    
    public void setCodigoBarras (String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public String getNomeProduto () {
        return this.nomeProduto;
    }
    
    public void setNomeProduto (String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    
    public String getFabricante () {
        return this.fabricante;
    }
    
    public void setFabricante (String fabricante) {
        this.fabricante = fabricante;
    }
    
    public String getMarca () {
        return this.marca;
    }
    
    public void setMarca (String Marca) {
        this.marca = marca;
    }
    
    public String getDataFabricaçao () {
        return this.dataFabricacao;
    }
    
    public void setDataFabricaçao (String dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }
    
    public long getQuantidade () {
        return this.quantidade;
    }
    
    public void setQuantidade (long quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getValor () {
        return this.valor;
    }
    
    public void setValor (String valor) {
        this.valor = valor;
    }
    
    public String getTotal () {
        return this.total;
    }
    
    public void setTotal (String total) {
        this.total = total;
    }
}
