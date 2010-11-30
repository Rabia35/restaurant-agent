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
        SpriteFont font;

       // private Texture2D textureAlto;
       // private AgenteBodego Alto;

        Vector2 posX = new Vector2(200.0f, 450.0f);
        Vector2 posY = new Vector2(300.0f, 450.0f);
        Vector2 postxt = new Vector2(400.0f, 450.0f);

       // int  X;
        TextReader txtAlto,txtMedio,txtBajo, txtProveedor;
       // int  Y;
        int[] traduceY = {384,320,256,192,128,64,0};

        int numEstantesTotal = 48;
        int numPisoTotal = 111;

//        private KeyboardState teclado;

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

            Texture2D cajaAgente = Content.Load<Texture2D>("caja32");
            Texture2D cajaLlegada = Content.Load<Texture2D>("Caja42");

            llegada = new Llegada(Content.Load<Texture2D>("Llegada"), cajaLlegada);
            salida = new Salida(Content.Load<Texture2D>("Salida"));
            Alto = new Bodego(Content.Load<Texture2D>("Alto"), cajaAgente);
            Medio = new Bodego(Content.Load<Texture2D>("Medio"), cajaAgente);
            Bajo = new Bodego(Content.Load<Texture2D>("Bajo"), cajaAgente);

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


            try
            {

                txtAlto = new StreamReader(
                    "C:\\Users\\Zeraal\\Desktop\\Alto.txt");
                    //"D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Alto.txt");
                txtMedio = new StreamReader(
                    "C:\\Users\\Zeraal\\Desktop\\Medio.txt");
                    //"D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Normal.txt");
                txtBajo = new StreamReader(
                    "C:\\Users\\Zeraal\\Desktop\\Bajo.txt");
                    //"D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Fuerte.txt");
                txtProveedor = new StreamReader(
                    "C:\\Users\\Zeraal\\Desktop\\Proveedor.txt");
                    //"D:\\Mis Documentos\\Tec\\Sistemas Multiagente\\restaurant-agent\\restaurant-agent\\Proveedor.txt");
            }
            catch (Exception)
            {
                txtAlto.Close();
                txtBajo.Close();
                txtMedio.Close();
                txtProveedor.Close();
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

            if(Alto.ingrediente != null && Alto.ingrediente.Length > 0)
                spriteBatch.Draw(Alto.caja, Alto.getPosition(), Color.White);
            if (Medio.ingrediente != null && Medio.ingrediente.Length > 0)
                spriteBatch.Draw(Medio.caja, Medio.getPosition(), Color.White);
            if (Bajo.ingrediente != null && Bajo.ingrediente.Length > 0)
                spriteBatch.Draw(Bajo.caja, Bajo.getPosition(), Color.White);

            if (llegada.paquete1 != null && llegada.paquete1.CompareTo("0") != 0)
                spriteBatch.Draw(llegada.caja, llegada.getPosition(), Color.White);
            if (llegada.paquete2 != null && llegada.paquete2.CompareTo("0") != 0)
                spriteBatch.Draw(llegada.caja,new Vector2(llegada.getPosition().X,llegada.getPosition().Y+64), Color.White);
            if (llegada.paquete3 != null && llegada.paquete3.CompareTo("0")!= 0 )
                spriteBatch.Draw(llegada.caja, new Vector2(llegada.getPosition().X, llegada.getPosition().Y + 128), Color.White);
            
          //  base.Draw(gameTime);
            /*
            spriteBatch.DrawString(font, "X: " + X.ToString(), posX, Color.White);
            spriteBatch.DrawString(font, "Y: " + Y.ToString(), posY, Color.White);
            spriteBatch.DrawString(font, "Coso: " + coso, postxt, Color.White);
*/
            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
