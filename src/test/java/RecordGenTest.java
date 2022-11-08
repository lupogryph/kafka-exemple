import fr.uge.data.RecordGenerator;

public class RecordGenTest {

    public static void main(String args[]) {
        RecordGenerator rg = new RecordGenerator();
        System.out.println(rg.getNom()+" "+rg.getPrenom()+ " idProduit="+rg.getIdProduit() +" prix =  $"+ rg.getPrix());
    }
}
