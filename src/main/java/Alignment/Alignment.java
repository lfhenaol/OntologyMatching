package Alignment;

import LOM.*;
import Ontology.Ontology;
import org.apache.jena.ontology.OntModel;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Alignment {
    private Ontology ontology;
    private OntModel modelAlignment;

    public Alignment(Ontology ontoObject){
        this.ontology = ontoObject;
    }

    public void match(LOM lomObject){

    }

    public void match(General generalObject){

    }

    public void match(Educational educationalObject){

    }

    public void match(Technical technicalObject){

    }

    public void match(Classification classificationObject){

    }
}
