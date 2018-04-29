package sprites;

        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.math.Rectangle;
        import com.badlogic.gdx.math.Vector2;
        import help.Info;


        public class Paddle extends Sprite {
            private Vector2 positionM;
            private Vector2 speed;
            private Rectangle bounds;
            private float resize;
            private int giocatore;

            public Paddle(int numeroGiocatori, int giocatore) {
                super(new Texture("mattonalla curva.png"));
                this.resize=Info.paddleresize;
                this.giocatore = giocatore;
                positionM = new Vector2((Info.larghezza / numeroGiocatori) * (giocatore - 1) + Info.larghezza / (2 * numeroGiocatori) - this.getWidth() / 2 * Info.paddleresize, 0);
                speed = new Vector2(0, 0);
                this.resize = resize;
                bounds = new Rectangle(positionM.x, positionM.y, Paddle.this.getWidth() * Info.paddleresize, Paddle.this.getHeight() * Info.paddleresize);
//ilboundsiriferisconoalrettangoloinvisibilechecostruiròintornoallatexture
//l'altezzaelalarghezzasonodellatexture
            }

            public void setDefaultState(int numeroGiocatori) {
                positionM = new Vector2((Info.larghezza / numeroGiocatori) * (giocatore - 1) + Info.larghezza / (2 * numeroGiocatori) - this.getWidth() / 2 * Info.paddleresize, 0);
                speed = new Vector2(0, 0);
                bounds = new Rectangle(positionM.x, positionM.y, Paddle.this.getWidth() * Info.paddleresize, Paddle.this.getHeight() * Info.paddleresize);
            }
            public Vector2 getPosition() {
                return positionM;
            }

            public Vector2 getSpeed() {
                return speed;
            }

            public void setPositionM(float x) {
                this.positionM.x = x;
            }

            public Rectangle getBounds() {
                return bounds;
            }
        }
