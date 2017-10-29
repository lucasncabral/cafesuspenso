package com.cafesuspenso.ufcg.cafesuspenso.Model;

public class QRCodeData {
    private Long id;
    private String placename;
    private Integer numberProduct;
    private String imagem;
    private Product product;

    public QRCodeData(Long id, String nome, Integer numCoffees, String imagem, Product product) {
        this.id = id;
        this.placename = nome;
        this.numberProduct = numCoffees;
        this.imagem = imagem;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return placename;
    }

    public void setName(String name) {
        this.placename = name;
    }

    public Integer getNumCoffees() {
        return numberProduct;
    }

    public void setNumCoffees(Integer numCoffees) {
        this.numberProduct = numCoffees;
    }

    public String getPlaceImg() {
        return imagem;
    }

    public void setPlaceImg(String placeImg) {
        this.imagem = placeImg;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public Integer getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(Integer numberProduct) {
        this.numberProduct = numberProduct;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
