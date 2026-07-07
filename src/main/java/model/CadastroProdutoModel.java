package model;
public class CadastroProdutoModel {
    private String codigoBarras, nomeProduto, fabricante, marca, dataFabricacao, dataVencimento, valor, total, status, prateleira;
    private long quantidade, qtdMinima;
    public CadastroProdutoModel() {}
    public String getCodigoBarras() { return codigoBarras; } public void setCodigoBarras(String v) { codigoBarras = v; }
    public String getNomeProduto()  { return nomeProduto;  } public void setNomeProduto(String v)  { nomeProduto = v;  }
    public String getFabricante()   { return fabricante;   } public void setFabricante(String v)   { fabricante = v;   }
    public String getMarca()        { return marca;        } public void setMarca(String v)        { marca = v;        }
    public String getDataFabricacao() { return dataFabricacao; } public void setDataFabricacao(String v) { dataFabricacao = v; }
    public String getDataVencimento() { return dataVencimento; } public void setDataVencimento(String v) { dataVencimento = v; }
    public long   getQuantidade()   { return quantidade;   } public void setQuantidade(long v)    { quantidade = v;   }
    public String getValor()        { return valor;        } public void setValor(String v)        { valor = v;        }
    public String getTotal()        { return total;        } public void setTotal(String v)        { total = v;        }
    public String getStatus()       { return status;       } public void setStatus(String v)       { status = v;       }
    public String getPrateleira()   { return prateleira;   } public void setPrateleira(String v)   { prateleira = v;   }
    public long   getQtdMinima()    { return qtdMinima;    } public void setQtdMinima(long v)     { qtdMinima = v;    }
}
