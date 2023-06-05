/*
 * Copyright 2020 Jules Hauchecorne  , kousseyla kadri, Martine Lemelin , Jean-Michel Landry .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import net.sf.json.JSONObject;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.junit.jupiter.api.Test;


import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class validationFormationFichierTest {

    /**
     * TEST METHODE formatDate
     */

    @Test
    public void testFormatDate() throws ParseException {
        assertTrue(ValidationFichier.formatDate("2000-12-23"));
    }

    @Test
    public void testFormatDateJours()  throws ParseException {
        assertFalse(ValidationFichier.formatDate("20090-12-232"));
    }

    @Test
    public void testFormatDateMois()  throws ParseException {
        assertFalse(ValidationFichier.formatDate("2000-3-12"));
    }

    @Test
    public void testFormatDateVide()  throws ParseException {
        assertFalse(ValidationFichier.formatDate(" "));
    }

    @Test
    public void testFormatDateParseException()  throws ParseException {
        assertFalse(ValidationFichier.formatDate("2003-44-44"));
    }

    @Test
    public void testFormatDateAnnee()  throws ParseException {
        assertFalse(ValidationFichier.formatDate("200-03-12"));
    }

    @Test
    public void testFormatDateZero()  throws ParseException {
        assertFalse(ValidationFichier.formatDate("0000-00-00"));
    }

    /**
     * TEST DureeA ctivite
     */

    @Test
    void testDureeActiviteNegative() {
        assertFalse(ValidationFichier.DureeActivite(-1));
    }

    /**
     * Num de Permis Valide
     * exeption StringIndexOutOfBoundsException
     */

    @Test
    void testNumPermisValideVide() {
        // ici StringIndexOutOfBoundsException
        assertFalse(ValidationFichier.estNumPermisValide(""));
    }


    @Test
    void testNumPermisValideLettreMajuscule() {
        assertFalse(ValidationFichier.estNumPermisValide("a4444"));
    }

    @Test
    void testNumPermisValideLettreMajusculeA() {
        assertTrue(ValidationFichier.estNumPermisValide("A4444"));
    }

    @Test
    void testNumPermisValideLettreMajusculeS() {
        assertTrue(ValidationFichier.estNumPermisValide("S4444"));
    }

    @Test
    void testNumPermisValide4ChiffresR() {
        assertTrue(ValidationFichier.estNumPermisValide("R4444"));
    }

    @Test
    void testNumPermisValide4ChiffresZ() {
        assertTrue(ValidationFichier.estNumPermisValide("Z4444"));
    }

    @Test
    void testNumPermisValide4Chiffres() {
        assertFalse(ValidationFichier.estNumPermisValide("A44444"));
    }

    @Test
    void testNumPermisValideAutre() {
        assertFalse(ValidationFichier.estNumPermisValide("-@k 2‡"));
    }

    /**
     * TEST Est Description
     */

    @Test
    void testEstDescriptionPresenteNbrCaracteres20() {
        assertFalse(ValidationFichier.estDescriptionPresente("HIGKLMNOPETUH NOKJr-"));
    }

    @Test
    void testEstCycleValide (){
        JSONObject declarationVrai = new DeclarationAutreOrdre().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFichier.estCycleValide(declarationVrai));
    }

    @Test
    void testEstCycleValideFaux(){
        JSONObject declarationFaux = new DeclarationAutreOrdreFaux().getDeclarationJSON_EntreFaux();
        assertFalse(ValidationFichier.estCycleValide(declarationFaux));
    }

    @Test
    void testEstCycleValideArchitectes (){
        JSONObject declarationVrai = new DeclarationArchitectes().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFichier.estCycleValide(declarationVrai));
    }

    @Test
    void testEstCycleValidePsy (){
        JSONObject declarationVrai = new DeclarationOrdrePsy().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFichier.estCycleValide(declarationVrai));
    }

    @Test
    void testFichierValide() throws ParseException {
        JSONObject declarationVrai = new DeclarationAutreOrdre().getDeclarationJSON_EntreValide();
        assertTrue(ValidationFichier.estFichierValide(declarationVrai));
    }

    @Test
    void testFichierInvalide () throws ParseException {
        JSONObject declarationFaux = new DeclarationAutreOrdreFaux().getDeclarationJSON_EntreFaux();
        assertFalse(ValidationFichier.estFichierValide(declarationFaux));
    }
}

