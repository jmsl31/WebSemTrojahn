import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.impl.method.ClassStructAlignment;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;
import fr.inrialpes.exmo.align.impl.method.SMOANameAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import fr.inrialpes.exmo.align.parser.AlignmentParser;
import jade.util.leap.Properties;

public class Tp1 {
	public  static  void  render(Alignment  alignment,String path) throws  FileNotFoundException
	, UnsupportedEncodingException, AlignmentException {
	PrintWriter  writer;
	FileOutputStream f = new  FileOutputStream(new  File(path));
	writer = new  PrintWriter(new  BufferedWriter(new  OutputStreamWriter(f,"UTF-8")), true);
	AlignmentVisitor  renderer = new  RDFRendererVisitor(writer);
	alignment.render(renderer);
	writer.flush();
	writer.close();
	}
	
	public  static  void  evaluate(Alignment  alignment) throws  URISyntaxException ,
	AlignmentException {
	URI  reference = new  URI("http://oaei.ontologymatching.org/tests/302/refalign.rdf");
	AlignmentParser  aparser = new  AlignmentParser (0);
	Alignment  refalign = aparser.parse(reference);
	PRecEvaluator  evaluator = new  PRecEvaluator(refalign , alignment);
	evaluator.eval(new  Properties ());
	System.out.println("Precision : " + evaluator.getPrecision ());
	System.out.println("Recall :"      + evaluator.getRecall ());
	System.out.println("FMeasure :"    + evaluator.getFmeasure ());
	}
	
	
	public  static  void  generateAlign ()  throws  URISyntaxException ,
	AlignmentException, FileNotFoundException, UnsupportedEncodingException {
	URI  onto1 = new  URI("http://oaei.ontologymatching.org/tests/101/onto.rdf"
	);
	URI  onto2 = new  URI("http://oaei.ontologymatching.org/tests/302/onto.rdf"
	);
	AlignmentProcess  alignment = new  NameEqAlignment ();
	alignment.init (onto1 , onto2);
	alignment.align(null , new  Properties ());
	render(alignment,"NameEqAligment.rdf");
	evaluate(alignment);
	System.out.println("Num  corresp. générées : " + alignment.nbCells ());
	
	alignment = new  EditDistNameAlignment ();
	alignment.init (onto1 , onto2);
	alignment.align(null , new  Properties ());
	render(alignment,"EditDistNameAlignment.rdf");
	evaluate(alignment);
	System.out.println("Num  corresp. générées : " + alignment.nbCells ());
	
	alignment = new  SMOANameAlignment ();
	alignment.init (onto1 , onto2);
	alignment.align(null , new  Properties ());
	render(alignment,"SMOANameAlignment.rdf");
	evaluate(alignment);
	System.out.println("Num  corresp. générées : " + alignment.nbCells ());
	
	alignment = new  NameAndPropertyAlignment ();
	alignment.init (onto1 , onto2);
	alignment.align(null , new  Properties ());
	render(alignment,"NameAndPropertyAlignment.rdf");
	evaluate(alignment);
	System.out.println("Num  corresp. générées : " + alignment.nbCells ());
	
	alignment = new  ClassStructAlignment ();
	alignment.init (onto1 , onto2);
	alignment.align(null , new  Properties ());
	render(alignment,"ClassStructAlignment.rdf");
	evaluate(alignment);
	System.out.println("Num  corresp. générées : " + alignment.nbCells ());
	}
	
	
	public static void main(String[] args) throws URISyntaxException, AlignmentException, FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		 generateAlign();
		 
	}

}
