using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

namespace Restaurant_Agent
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;
        Rectangle viewPortRec;
        Estante[] estantes;
        Piso[] pisos;
        Llegada llegada;
        Salida salida;
        Bodego Alto,Medio,Bajo;
        int AltoX, AltoY, MedioX, MedioY, BajoX, BajoY;


        SpriteFont font;

       // private Texture2D textureAlto;
       // private AgenteBodego Alto;

        Vector2 posX = new Vector2(200.0f, 450.0f);
        Vector2 posY = new Vector2(300.0f, 450.0f);
        Vector2 poscoso = new Vector2(400.0f, 450.0f);

       // int  X;
        TextReader txtAlto,txtMedio,txtBajo;
       // int  Y;
        int[] traduceY = {384,320,256,192,128,64,0};


        char Altocharx,Altochar2x,Altochary,
             Mediocharx, Mediochar2x,Mediochary,
             Bajocharx, Bajochar2x, Bajochary;
        
        string AltoCaja, MedioCaja, BajoCaja;
        string textAlto,textBajo,textMedio;

        int numEstantesTotal = 48;
        int numPisoTotal = 111;
        int sumaNumEstantes = 0;
        int sumaNumPiso = 0;
        int numEstantes = 12;
        int estLargo = 3;
        int estAlto = 4;
        private Texture2D estanteTexture;

        private KeyboardState teclado;

        private TimeSpan elapsed = new TimeSpan(0,0,0,0,0);

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            graphics.PreferredBackBufferHeight = 448 + 50;
            graphics.PreferredBackBufferWidth = 1216;

            Content.RootDirectory = "Content";
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            Services.AddService(typeof(SpriteBatch), spriteBatch);
                
            viewPortRec  = new Rectangle(0, 0,
                graphics.GraphicsDevice.Viewport.Width,
                graphics.GraphicsDevice.Viewport.Height);
            font = Content.Load<SpriteFont>("font");
            //textureAlto= Content.Load<Texture2D>("Alto");
            

            estantes = new Estante[numEstantesTotal];
            pisos = new Piso[numPisoTotal];

            for (int i = 0; i < numEstantesTotal; i++) 
            {
                estantes[i] = new Estante(Content.Load<Texture2D>("Estante")); 
            }
            for (int i = 0; i < numPisoTotal; i++) 
            {
                pisos[i] = new Piso(Content.Load<Texture2D>("piso"));
            }

            Texture2D caja = Content.Load<Texture2D>("caja32");

            llegada = new Llegada(Content.Load<Texture2D>("Llegada"));
            salida = new Salida(Content.Load<Texture2D>("Salida"));
            Alto = new Bodego(Content.Load<Texture2D>("Alto"), caja);
            Medio = new Bodego(Content.Load<Texture2D>("Medio"), caja);
            Bajo = new Bodego(Content.Load<Texture2D>("Bajo"), caja);
           // Alto = new AgenteBodego(this, ref textureAlto, "Alto");

            llegada.setPosition(new Vector2(0,0));
            salida.setPosition(new Vector2(1152, 0));

           // bodego.setPosition(new Vector2(64, 64));
            
            DibujaPiso();

            DibujaEstantes(0, 192);
            DibujaEstantes(384, 192);
            DibujaEstantes(768, 192);
            DibujaEstantes(1152, 192);


            // TODO: use this.Content to load your game content here
        }

        public void DibujaEstantes(int sumaX,int sumaY)
        {
            for (int i = 0; i < estLargo; i++)
            {
                for (int j = 0; j < estAlto; j++)
                {
                    estantes[sumaNumEstantes].setPosition(new Vector2(i * Estante.ESTANTEWIDTH + sumaX, j * Estante.ESTANTEHEIGHT + sumaY));
                    sumaNumEstantes++;
                }
            }

        }
        public void DibujaPiso() 
        {
            for(int i=0;i<17;i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    pisos[sumaNumPiso].setPosition(new Vector2(i * Piso.PISOWH + 64, j * Piso.PISOWH));
                    sumaNumPiso++;
                }
            }

            DibujaPisoPasillo(64,192);
            DibujaPisoPasillo(448, 192);
            DibujaPisoPasillo(832, 192);
        }

        public void DibujaPisoPasillo(int sumX,int sumY)
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    pisos[sumaNumPiso].setPosition(new Vector2(i * Piso.PISOWH + sumX , j * Piso.PISOWH + sumY ));
                    sumaNumPiso++;
                }
            }
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            if (gameTime.TotalGameTime.Seconds == elapsed.Seconds)
                return;
            elapsed = gameTime.TotalGameTime;


            try
            {

                txtAlto = new StreamReader("D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Alto.txt");
                txtMedio = new StreamReader("D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Normal.txt");
                txtBajo = new StreamReader("D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Fuerte.txt");
            }
            catch (Exception)
            {
                txtAlto.Close();
                txtBajo.Close();
                txtMedio.Close();
                return;
            }
           AltoX = MetodoX(Int16.Parse(txtAlto.ReadLine()));
           AltoY = MetodoY(Int16.Parse(txtAlto.ReadLine()));
           Alto.ingrediente = txtAlto.ReadLine();
           MedioX =MetodoX(Int16.Parse(txtMedio.ReadLine()));
           MedioY =MetodoY(Int16.Parse(txtMedio.ReadLine()));
           Medio.ingrediente = txtMedio.ReadLine();
           BajoX = MetodoX(Int16.Parse(txtBajo.ReadLine()));
           BajoY = MetodoY(Int16.Parse(txtBajo.ReadLine()));
           Bajo.ingrediente = txtBajo.ReadLine();

           txtAlto.Close();
           txtBajo.Close();
           txtMedio.Close();

           Alto.setPosition(new Vector2(AltoX, AltoY));
           Medio.setPosition(new Vector2(MedioX, MedioY));
           Bajo.setPosition(new Vector2(BajoX, BajoY));

//            coso = "";

            //32 es enter
/*
            for(int i = 5; i < prueba.Length; i++)
            {
                coso += prueba[i];
            }
*/
                // close the stream
                

            



            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            /*
            teclado = Keyboard.GetState();

            if (teclado.IsKeyDown(Keys.Up))
                bodego.position += new Vector2(0, -64);
            if (teclado.IsKeyDown(Keys.Down))
                bodego.position += new Vector2(0, +64);
            if (teclado.IsKeyDown(Keys.Left))
                bodego.position += new Vector2(-64, 0);
            if (teclado.IsKeyDown(Keys.Right))
                bodego.position += new Vector2(+64, 0);
              */
            // TODO: Add your update logic here

           // base.Update(gameTime);
        }
    public int MetodoX(int numero)
    {
        return (numero - 1) * 64;
    }

    public int MetodoY(int y)
    {
        int Y;
        Y = traduceY[y - 1];
        return Y;
    }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);
            spriteBatch.Begin();
            
            foreach(Estante estante in estantes) 
            {
                spriteBatch.Draw(estante.getImage(),estante.getPosition(),Color.White);
            }

            foreach (Piso piso in pisos)
            {
                spriteBatch.Draw(piso.getImage(), piso.getPosition(), Color.White);
            }

            spriteBatch.Draw(llegada.getImage(), llegada.getPosition(), Color.White);
            spriteBatch.Draw(salida.getImage(), salida.getPosition(), Color.White);


            spriteBatch.Draw(Alto.getImage(), Alto.getPosition(), Color.White);
            spriteBatch.Draw(Medio.getImage(), Medio.getPosition(), Color.White);
            spriteBatch.Draw(Bajo.getImage(), Bajo.getPosition(), Color.White);

            if(Alto.ingrediente != null && Alto.ingrediente.Length > 0)
                spriteBatch.Draw(Alto.caja, Alto.getPosition(), Color.White);
            if (Medio.ingrediente != null && Medio.ingrediente.Length > 0)
                spriteBatch.Draw(Medio.caja, Medio.getPosition(), Color.White);
            if (Bajo.ingrediente != null && Bajo.ingrediente.Length > 0)
                spriteBatch.Draw(Bajo.caja, Bajo.getPosition(), Color.White);
            
          //  base.Draw(gameTime);
            /*
            spriteBatch.DrawString(font, "X: " + X.ToString(), posX, Color.White);
            spriteBatch.DrawString(font, "Y: " + Y.ToString(), posY, Color.White);
            spriteBatch.DrawString(font, "Coso: " + coso, poscoso, Color.White);
*/



            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
