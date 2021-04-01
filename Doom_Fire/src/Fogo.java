import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * @author vinic
 *
 */
public class Fogo {
	private static int alturaDoFogo = 60;
	private static int larguraDoFogo = 60;
	private static int paletaDeCores = 36;
	
	private int[] arrayDePixelsDoFogo;
	private static Color[] colorArray = {new Color(7, 7, 7), new Color(31, 7, 7), new Color(47, 15, 7),
			new Color(71, 15, 7), new Color(87, 23, 7), new Color(103, 31, 7), new Color(119, 31, 7),
			new Color(143, 39, 7), new Color(159, 47, 7), new Color(175, 63, 7), new Color(191, 71, 7),
			new Color(199, 71, 7), new Color(223, 79, 7), new Color(223, 87, 7), new Color(223, 87, 7),
			new Color(215, 95, 7), new Color(215, 95, 7), new Color(215, 103, 15), new Color(207, 111, 15),
			new Color(207, 119, 15), new Color(207, 127, 15), new Color(207, 135, 23), new Color(207, 135, 23),
			new Color(199, 135, 23), new Color(199, 143, 23), new Color(199, 151, 31), new Color(191, 159, 31),
			new Color(191, 159, 31), new Color(191, 167, 39), new Color(191, 167, 39), new Color(191, 175, 47),
			new Color(183, 175, 47), new Color(183, 183, 47), new Color(183, 183, 55), new Color(207, 207, 111),
			new Color(223, 223, 159), new Color(239, 239, 199), new Color(255, 255, 255)};
	
	private int temporario = 0;
	private boolean debug = false;
	
	// Construtor
	public Fogo() {
		criarEstruturaDeDados();
		//mostrarEstruturaDoFogo();
		criarFonteDeFogo();
	}

	private void criarEstruturaDeDados() {
		//Matriz linear que representa o fogo
		arrayDePixelsDoFogo = new int[larguraDoFogo * alturaDoFogo];
		//Zerando matriz linear
		for (int i = 0; i < arrayDePixelsDoFogo.length; i++){
			arrayDePixelsDoFogo[i] = 0;
		}
		//Mostrando o tamanho de elementos na matriz linear
		//System.out.println("Tamanho do array de fogo é:" + arrayDePixelsDoFogo.length);
	}
	
	private void mostrarEstruturaDoFogo() {
		//Intera na matriz linear printando o conteudo 
		for (int linha = 0; linha < alturaDoFogo; linha++) {
			System.out.println("");
			for (int coluna = 0; coluna < larguraDoFogo; coluna++) {
				int index_Pixel = coluna + (larguraDoFogo * linha);
				//int itensidade_De_Fogo = arrayDePixelsDoFogo[index_Pixel];
				
				System.out.print(arrayDePixelsDoFogo[index_Pixel]);
			}
			
		}
		System.out.println("\n--------------------------------------");
	
	}
	
	private void criarFonteDeFogo() {
		for (int coluna = 0; coluna < larguraDoFogo; coluna++) {
			int pixelFogoSobrePosto = larguraDoFogo * alturaDoFogo;
			int indecePixel = (pixelFogoSobrePosto - larguraDoFogo) + coluna;
			arrayDePixelsDoFogo[indecePixel] = paletaDeCores;
		}
			
	}

	// Animaçao
	private void calcularPropagacaoDoFogo() {
		for (int coluna = 0; coluna < larguraDoFogo; coluna++) {
			for (int linha = 0; linha < alturaDoFogo; linha++) {
				int indicePixelAtual = coluna + (larguraDoFogo * linha);
				atualizarIntensidadeDoFogo(indicePixelAtual);
			}
			
		}
		//mostrarEstruturaDoFogo();

	}

	private void atualizarIntensidadeDoFogo(int indicePixelAtual) {
		Random r = new Random();
		
		int indicePixelAbaixo = indicePixelAtual + larguraDoFogo;
		
		if(indicePixelAbaixo >= alturaDoFogo * larguraDoFogo) {
			return;
		}
		//int declinio = 1;
		int declinio = r.nextInt(3);
		int intensidadePixelAbaixo = arrayDePixelsDoFogo[indicePixelAbaixo];
		
		int novaIntensidade = intensidadePixelAbaixo - declinio >= 0? intensidadePixelAbaixo - declinio : 0 ;
		
		
		if((indicePixelAtual - declinio) <= -1) {
			declinio = 0;
		}
		
		arrayDePixelsDoFogo[indicePixelAtual - (declinio)] = novaIntensidade; 
		//arrayDePixelsDoFogo[indicePixelAtual] = novaIntensidade;
	}
		
	public void render(Graphics g) {
		g.setColor(new Color(0,255,0));
		g.setFont(new Font("Arial", Font.BOLD, 22));
		
		g.drawString("Estrutura de dados = " + arrayDePixelsDoFogo.length, 20, 550);
		g.drawString("Estrutura de cores = " + colorArray.length, 20, 550);
		
		for (int y = 0; y < alturaDoFogo; y++) {
			for (int x = 0; x < larguraDoFogo; x++) {
				
				int posX = x * 16;
				int posY = y * 16;
				
				if(debug) {
					g.drawString(" " + arrayDePixelsDoFogo[temporario], posX, posY);
				}else {
					g.setColor(colorArray[arrayDePixelsDoFogo[temporario]]);
					g.fillRect(posX, posY, 16, 16);
				}
				temporario++;
			}
		}
		
		temporario = 0;
		
	}
	
	public void tick() {
		//mostrarEstruturaDoFogo();
		calcularPropagacaoDoFogo();
	}
}