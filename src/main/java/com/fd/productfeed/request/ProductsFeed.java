package com.fd.productfeed.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder( { "utoken", "products" })
public class ProductsFeed
{

    @JsonProperty("utoken")
    private String utoken;
    @JsonProperty("products")
    private Map<String, Product> products = new HashMap<String, Product>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("utoken")
    public String getUtoken()
    {
        return utoken;
    }

    @JsonProperty("utoken")
    public void setUtoken(String utoken)
    {
        this.utoken = utoken;
    }

    @JsonProperty("products")
    public Map<String, Product> getProducts()
    {
        return this.products;
    }

    @JsonProperty("products")
    public void setProducts(Map<String, Product> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }
}