package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Taxon {
    private Resource id;
    private Resource entry;
    private OntModel taxonModel;
    Taxon(JsonNode jsonTaxon){
        this.taxonModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        this.id = undefinedToResource(jsonTaxon.getNode("id").getText());
        this.entry = undefinedToResource(jsonTaxon.getNode("entry").getText());
    }

    public Map match(Ontology onto){

        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.id);
        localProp.add(this.entry);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("id");
        jresponse.add("entry");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.taxonModel.createObjectProperty(rs.toString());
            this.taxonModel.createObjectProperty(lc.toString());
            this.taxonModel.createSymmetricProperty(lc.toString());
            this.taxonModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }
        json = json + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.taxonModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.taxonModel.createResource(uri) : this.taxonModel.createResource(localProperty);
    }

    public Resource getId() {
        return id;
    }

    public void setId(Resource id) {
        this.id = id;
    }

    public Resource getEntry() {
        return entry;
    }

    public void setEntry(Resource entry) {
        this.entry = entry;
    }

    public OntModel getTaxonModel() {
        return taxonModel;
    }

    public void setTaxon(OntModel taxon) {
        this.taxonModel = taxon;
    }
}
