import org.junit.jupiter.api.Test;

public class AnimationTest {

	@Test
	public void animate() {
		while (true) {
			printSpinningBarAnimation("Loading");
			try {
				Thread.sleep(100); // Pausa para crear la animación
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}

		}

	}
	
	private void printSpinningBarAnimation(String msg) {
	    String bars = "|/-\\";
	    int i = (int) ((System.currentTimeMillis() / 100) % bars.length());
	    
	    // Imprimir el mensaje y el bar, y limpiar la línea
	    String output = String.format("%s %c", msg, bars.charAt(i));
	    System.out.print(output);
	    // Agregar espacios para limpiar la línea
	    System.out.print(" ".repeat(Math.max(0, 40 - output.length()))); // Ajusta el 40 según sea necesario
	    System.out.print("\r");
	    System.out.flush();
	}



}
