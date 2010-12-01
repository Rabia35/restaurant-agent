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
        Bodego Alto, Medio, Bajo;
        Chef awesomeChef;
        Menu codicioso, saludable, prudente;
        SpriteFont font;
        string rutaBarbie = "D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\";
        string rutaMetal = "C:\\Users\\Zeraal\\Desktop\\";

        Vector2 posX = new Vector2(00.0f, 450.0f);
        Vector2 posY = new Vector2(00.0f, 470.0f);
        Vector2 postxt = new Vector2(400.0f, 450.0f);

  

       // int  X;
        TextReader txtAlto,txtMedio,txtBajo, txtProveedor, txtChef,txtCodicioso, txtSaludable, txtPrudente;
       // int  Y;
        int[] traduceY = {384,320,256,192,128,64,0};

        int numEstantesTotal = 48;
        int numPisoTotal = 111;

//        private KeyboardState teclado;

        private TimeSpan elapsed = new TimeSpan(0,0,0,0,0);

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            graphics.PreferredBackBufferHeight = 448 + 115+23;
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

            Texture2D cajaAgente = Content.Load<Texture2D>("caja32");
            Texture2D cajaLlegada = Content.Load<Texture2D>("Caja42");
            Texture2D globo = Content.Load<Texture2D>("globo");

            llegada = new Llegada(Content.Load<Texture2D>("Llegada"), cajaLlegada);
            salida = new Salida(Content.Load<Texture2D>("Salida"));
            Alto = new Bodego(Content.Load<Texture2D>("Alto"), cajaAgente);
            Medio = new Bodego(Content.Load<Texture2D>("Medio"), cajaAgente);
            Bajo = new Bodego(Content.Load<Texture2D>("Bajo"), cajaAgente);

            awesomeChef = new Chef(Content.Load<Texture2D>("Chef"), globo, "Chef");

            codicioso = new Menu(Content.Load<Texture2D>("menu"),codicioso, "Codicioso");
            prudente = new Menu(Content.Load<Texture2D>("menu"),prudente,"Prudente");
            saludable = new Menu(Content.Load<Texture2D>("menu"),saludable,"Saludabe");

            llegada.setPosition(new Vector2(0,0));
            salida.setPosition(new Vector2(1152, 0));
            
            pisos= Utilidades.DibujaPiso(pisos);
            estantes = Utilidades.DibujaEstantes(estantes);

            // TODO: use this.Content to load your game content here
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
           
            if (awesomeChef.visible)
            {
                awesomeChef.tiempoActual++;
                if (awesomeChef.tiempoDeVida <= awesomeChef.tiempoActual)
                    awesomeChef.visible = false;
            }

            if (codicioso.visible) 
            {
                codicioso.tiempoActual++;
                if (codicioso.tiempoDeVida <= codicioso.tiempoActual)
                    codicioso.visible = false;
            }
            if (prudente.visible)
            {
                prudente.tiempoActual++;
                if (prudente.tiempoDeVida <= prudente.tiempoActual)
                    prudente.visible = false;
            }
            if (saludable.visible)
            {
                saludable.tiempoActual++;
                if (saludable.tiempoDeVida <= saludable.tiempoActual)
                    saludable.visible = false;
            }
                       

            try
            {
                txtAlto = new StreamReader(
                    rutaMetal+"Alto.txt");

                //rutaBarbie+"Alto.txt");
                txtMedio = new StreamReader(
                    rutaMetal + "Medio.txt");
                //rutaBarbie+"Normal.txt");
                txtBajo = new StreamReader(
                    rutaMetal + "Bajo.txt");
                //rutaBarbie+"Fuerte.txt");
                txtProveedor = new StreamReader(
                    rutaMetal + "Proveedor.txt");
                //rutaBarbie+"Proveedor.txt");
                txtChef = new StreamReader(
                    rutaMetal+awesomeChef.nombre+".txt");
                //rutaBarbie+"Chef.txt");
                
                txtCodicioso = new StreamReader(
                    rutaMetal + "MenuCodicioso.txt");
                //rutaBarbie+"MenuCodicioso.txt");
                txtPrudente = new StreamReader(
                    rutaMetal + "MenuPrudente.txt");
                //rutaBarbie+"MenuPrudente.txt");
                txtSaludable =new StreamReader(
                    rutaMetal + "MenuSaludable.txt");
                //rutaBarbie+"MenuSaludable.txt");
                
            }
            catch (Exception)
            {
                txtAlto.Close();
                txtBajo.Close();
                txtMedio.Close();
                txtProveedor.Close();
                txtChef.Close();
                txtCodicioso.Close();
                txtPrudente.Close();
                txtSaludable.Close();
                return;
            }
            
           Alto.setPosition(new Vector2(
                Utilidades.TraducePosicionX(Int16.Parse(txtAlto.ReadLine())),
                Utilidades.TraducePosicionY(Int16.Parse(txtAlto.ReadLine()))));
           Alto.ingrediente = txtAlto.ReadLine();
           txtAlto.Close();

           Medio.setPosition(new Vector2(
               Utilidades.TraducePosicionX(Int16.Parse(txtMedio.ReadLine())),
               Utilidades.TraducePosicionY(Int16.Parse(txtMedio.ReadLine()))));
           Medio.ingrediente = txtMedio.ReadLine();
           txtMedio.Close();

           Bajo.setPosition(new Vector2(
               Utilidades.TraducePosicionX(Int16.Parse(txtBajo.ReadLine())),
               Utilidades.TraducePosicionY(Int16.Parse(txtBajo.ReadLine()))));
           Bajo.ingrediente = txtBajo.ReadLine();
           txtBajo.Close();

           llegada.paquete1 = txtProveedor.ReadLine();
           llegada.paquete2 = txtProveedor.ReadLine();
           llegada.paquete3 = txtProveedor.ReadLine();
           txtProveedor.Close();

           awesomeChef.temp = txtChef.ReadLine();

           if (awesomeChef.temp != null && awesomeChef.temp.Length > 0)
           {
               awesomeChef.ingrediente1 = awesomeChef.temp;
               awesomeChef.ingrediente2 = txtChef.ReadLine();
               awesomeChef.ingrediente3 = txtChef.ReadLine();
               awesomeChef.ingrediente4 = txtChef.ReadLine();
               awesomeChef.visible = true;
               awesomeChef.tiempoActual = 0;
           }
           txtChef.Close();

           (new StreamWriter(rutaMetal + awesomeChef.nombre+".txt")).Close();

           codicioso.temp = txtCodicioso.ReadLine();
           if (codicioso.temp != null && codicioso.temp.Length > 0)
           {
               codicioso.ingrediente1 = codicioso.temp;
               codicioso.ingrediente2 = txtCodicioso.ReadLine();
               codicioso.ingrediente3 = txtCodicioso.ReadLine();
               codicioso.visible = true;
               codicioso.tiempoActual = 0;
           }
           txtCodicioso.Close();
           (new StreamWriter(rutaMetal + "MenuCodicioso.txt")).Close();

            saludable.temp = txtSaludable.ReadLine();
           if (saludable.temp != null && saludable.temp.Length > 0)
           {
           saludable.ingrediente1 = saludable.temp;
           saludable.ingrediente2 = txtSaludable.ReadLine();
           saludable.ingrediente3 = txtSaludable.ReadLine();
           saludable.visible = true;
           saludable.tiempoActual = 0;
           }
           txtSaludable.Close();
           (new StreamWriter(rutaMetal + "MenuSaludable.txt")).Close();

           prudente.temp = txtPrudente.ReadLine();
            if (prudente.temp != null && prudente.temp.Length > 0)
           {
           prudente.ingrediente1 = prudente.temp;
           prudente.ingrediente2 = txtPrudente.ReadLine();
           prudente.ingrediente3 = txtPrudente.ReadLine();

           prudente.visible = true;
           prudente.tiempoActual = 0;
           }
            txtPrudente.Close();
            (new StreamWriter(rutaMetal + "MenuPrudente.txt")).Close();





            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            #region teclado (not in use)

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
            #endregion

            // TODO: Add your update logic here

           // base.Update(gameTime);
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

            spriteBatch.Draw(awesomeChef.getImage(), awesomeChef.getPosition(), Color.White);

            if (awesomeChef.visible)
            {
                spriteBatch.Draw(awesomeChef.globo, new Vector2(awesomeChef.getPosition().X + awesomeChef.chefW,
                    awesomeChef.getPosition().Y), Color.White);
                spriteBatch.DrawString(font, awesomeChef.ingrediente1 + "\n" +awesomeChef.ingrediente2 +
                                        "\n" + awesomeChef.ingrediente3 + "\n" + awesomeChef.ingrediente4 , 
                                        new Vector2(awesomeChef.getPosition().X + awesomeChef.chefW+40, awesomeChef.getPosition().Y+10), Color.Black);
            }
               
            if(Alto.ingrediente != null && Alto.ingrediente.Length > 0)
                spriteBatch.Draw(Alto.caja, Alto.getPosition(), Color.White);
            if (Medio.ingrediente != null && Medio.ingrediente.Length > 0)
                spriteBatch.Draw(Medio.caja, Medio.getPosition(), Color.White);
            if (Bajo.ingrediente != null && Bajo.ingrediente.Length > 0)
                spriteBatch.Draw(Bajo.caja, Bajo.getPosition(), Color.White);

            if (llegada.paquete1 != null && llegada.paquete1.CompareTo("0") != 0)
            {
                spriteBatch.Draw(llegada.caja, llegada.getPosition(), Color.White);
                spriteBatch.DrawString(font, llegada.paquete1, new Vector2(llegada.getPosition().X, llegada.getPosition().Y + llegada.cajaw), Color.White);
            }
            if (llegada.paquete2 != null && llegada.paquete2.CompareTo("0") != 0)
            {
                float posY = llegada.getPosition().Y + 64;
                spriteBatch.Draw(llegada.caja, new Vector2(llegada.getPosition().X, posY), Color.White);
                spriteBatch.DrawString(font, llegada.paquete2, new Vector2(llegada.getPosition().X, posY + llegada.cajaw), Color.White);
            }

            if (llegada.paquete3 != null && llegada.paquete3.CompareTo("0") != 0)
            {
                float posY = llegada.getPosition().Y + 128;
                spriteBatch.Draw(llegada.caja, new Vector2(llegada.getPosition().X, posY), Color.White);
                spriteBatch.DrawString(font, llegada.paquete3, new Vector2(llegada.getPosition().X, posY + llegada.cajaw), Color.White);
            }

            if (codicioso.visible)
            {
                float posX = codicioso.getPosition().X;
                spriteBatch.Draw(codicioso.getImage(), new Vector2(posX, codicioso.getPosition().Y), Color.White);
                spriteBatch.DrawString(font, "Menu codicioso:\n"+
                codicioso.ingrediente1+"\n"+codicioso.ingrediente2+"\n"+codicioso.ingrediente3,new Vector2(posX+10,codicioso.getPosition().Y+10), Color.Black);
            }
            if (saludable.visible)
            {
                float posX = saludable.getPosition().X + saludable.menuW;
                spriteBatch.Draw(saludable.getImage(), new Vector2(posX, saludable.getPosition().Y), Color.White);
                spriteBatch.DrawString(font, "Menu Saludable:\n" +
                saludable.ingrediente1 + "\n" + saludable.ingrediente2 + "\n" + saludable.ingrediente3, new Vector2(posX + 10, saludable.getPosition().Y + 10), Color.Black);
            }
            if (prudente.visible)
            {
                float posX = prudente.getPosition().X + prudente.menuW * 2;
                spriteBatch.Draw(prudente.getImage(), new Vector2(posX, prudente.getPosition().Y), Color.White);
                spriteBatch.DrawString(font, "Menu Prudente:\n" +
                prudente.ingrediente1 + "\n" + prudente.ingrediente2 + "\n" + prudente.ingrediente3, new Vector2(posX + 10, prudente.getPosition().Y + 10), Color.Black);
            }




            
          //  base.Draw(gameTime);
            
            spriteBatch.End();
            base.Draw(gameTime);
           
        }
    }
}
