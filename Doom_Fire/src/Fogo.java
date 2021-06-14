import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * @author Vinícius
 *
 * Classe geradora do fogo
 */
public class Fogo {
	
	//Variaveis de controle
	private static int alturaDoFogo = 60;
	private static int larguraDoFogo = 60;
	//intensidade do fogo; Valor máximo: 37
	//37 é a quantidade de núcleos da palheta original que os criadores dispunham na época
	private static int paletaDeCores = 37;
	
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
	
	private boolean debug = true;
	
	/**
	 * Função start da classe
	 */
	public Fogo() {
		criarEstruturaDeDados();
		criarFonteDeFogo();
	}

	/**
	 * Função que cria a estrutura de dados que armazenara as informações do fogo
	 */
	private void criarEstruturaDeDados() {
		
		//Matriz linear que representa o fogo
		arrayDePixelsDoFogo = new int[larguraDoFogo * alturaDoFogo];
		
		//Zerando matriz linear
		for (int i = 0; i < arrayDePixelsDoFogo.length; i++){
			arrayDePixelsDoFogo[i] = 0;
		}
		
		//Mostrando o quantidade de elementos na matriz linear
		//System.out.println("Tamanho do array de fogo é:" + arrayDePixelsDoFogo.length);
	}
	
	/**
	 * Função para verificar se a estrutura do fogo foi criado corretamente
	 */
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
	
	/**
	 * Função que cria a primeira linha de fogo, como se fosse o "início do incêndio";
	 * Seta a última linha, contando de cima para baixo, com a maior itnensidade do fogo, 36;
	 */
	private void criarFonteDeFogo() {
		for (int coluna = 0; coluna < larguraDoFogo; coluna++) {
			
			//Matemática para acessar a última linha do vetor linear
			
			//Comprimento total
			int tamanhoTotal = larguraDoFogo * alturaDoFogo;
			//Comprimento total menos a última linha, assim 'indecePixel' se encontra exatamente no primeiro elemento da última linha 
			int indecePixel = (tamanhoTotal - larguraDoFogo) + coluna;
			//atribui a posição atual a intensidade máxima do fogo
			arrayDePixelsDoFogo[indecePixel] = paletaDeCores;
		}
			
	}

	/**
	 * Animação;
	 * Esta função itera na estrutura de dados do vetor chamando para cada elemento a função "atualizarIntensidadeDoFogo", 
	 * a qual analisa e atualiza a intensidade do fogo;
	 */
	private void calcularPropagacaoDoFogo() {
		for (int coluna = 0; coluna < larguraDoFogo; coluna++) {
			for (int linha = 0; linha < alturaDoFogo; linha++) {
				
				//Matemática por conta do vetor linear interpretado como matriz
				int indicePixelAtual = coluna + (larguraDoFogo * linha);
				atualizarIntensidadeDoFogo(indicePixelAtual);
			}
			
		}
		if(debug) {
			mostrarEstruturaDoFogo();
		}
	}

	/**
	 * @param indicePixelAtual - indice no vetor de pixels que se quer alterar
	 */
	private void atualizarIntensidadeDoFogo(int indicePixelAtual) {
		Random r = new Random();
		
		//para verificar o pixel logo abaixo
		int indicePixelAbaixo = indicePixelAtual + larguraDoFogo;
		
		//Verificação de borda
		if(indicePixelAbaixo >= alturaDoFogo * larguraDoFogo) {
			return;
		}

		//Valor do desconto de intensidade do fogo, randomicamente
		int declinio = r.nextInt(3);
		
		// recupera a intesidade do pixel logo abaixo
		int intensidadePixelAbaixo = arrayDePixelsDoFogo[indicePixelAbaixo];
		
		//Efeito de decréscimo de intensidade com ruido (desconto aleatório)
		//Se intensidade do pixel de baixo menos o declínio aleatório for maior que zero, atribui; Se não, zera;
		int novaIntensidade = intensidadePixelAbaixo - declinio >= 0 ? intensidadePixelAbaixo - declinio : 0 ;
		
		//Verificação para não gerar resultados negativos
		if((indicePixelAtual - declinio) <= -1) {
			declinio = 0;
		}
		
		//Efeito de vento e atualizando intensidade
		//Ha uma probabilidade de atualizar a "indicePixelAtual" ou os adjacentes (O que cria o efeito do fogo)
		arrayDePixelsDoFogo[indicePixelAtual - (declinio)] = novaIntensidade; 
	}
		
	/**
	 * Função para renderizar o fogo em tela
	 * @param g - Instancia da imagem a ser mostrada na tela.
	 */
	public void renderFogo(Graphics g) {
		//Para modo DEBUG
		g.setColor(new Color(0,255,0));
		g.setFont(new Font("Arial", Font.BOLD, 13));
		
		//g.drawString("Estrutura de dados = " + arrayDePixelsDoFogo.length, 20, 550);
		//g.drawString("Estrutura de cores = " + colorArray.length, 20, 550);
		
		for (int y = 0; y < alturaDoFogo; y++) {
			for (int x = 0; x < larguraDoFogo; x++) {
				
				//Readequando ao tamanho da tela
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
	
	/**
	 * 
	 */
	public void tick() {
		calcularPropagacaoDoFogo();
	}
}