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
public class TaxonPath {
    private Resource source;
    private Taxon taxon;
    private OntModel taxonPathModel;

    TaxonPath(JsonNode jsonTaxonPath){
        this.taxonPathModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        this.source = this.undefinedToResource(jsonTaxonPath.getNode("source").getText());

        this.taxon = new Taxon(jsonTaxonPath.getNode("taxon"));
    }

    public Map match(Ontology onto){
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.source);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("source");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.taxonPathModel.createObjectProperty(rs.toString());
            this.taxonPathModel.createObjectProperty(lc.toString());
            this.taxonPathModel.createSymmetricProperty(lc.toString());
            this.taxonPathModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }

        Map result = this.taxon.match(onto);
        this.taxonPathModel.union(this.taxon.getTaxonModel());
        json = json +",\"taxon\":" +result.get("jsonObject").toString() + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.taxonPathModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.taxonPathModel.createResource(uri) : this.taxonPathModel.createResource(localProperty);
    }

    public Resource getSource() {
        return source;
    }

    public void setSource(Resource source) {
        this.source = source;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public OntModel getTaxonPathModel() {
        return taxonPathModel;
    }

    public void setTaxonPath(OntModel taxonPath) {
        this.taxonPathModel = taxonPath;
    }
}
