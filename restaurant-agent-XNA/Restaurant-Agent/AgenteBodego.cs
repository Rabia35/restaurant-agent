using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;


namespace Restaurant_Agent
{
    class AgenteBodego : Microsoft.Xna.Framework.DrawableGameComponent
    {
        protected Texture2D texture;

        public Vector2 position;

        public const int BodegoH = 64;
        public const int BodegoW = 64;
       // private Vector2 movimiento;
        protected SpriteBatch sBatch;
        public string NombreBodego;
        //TextReader tr;
        //string textoArchivo;
        //char charx, charx2, chary;
        int X, Y;
        int[] traduceY = { 384, 320, 256, 192, 128, 64, 0 };
        private TimeSpan elapsed = new TimeSpan(0, 0, 0, 0, 0);

        public AgenteBodego(Game game, ref Texture2D theTexture,string nombreBodego):base (game)
        {
                texture = theTexture;
                position = new Vector2();
                sBatch = (SpriteBatch)Game.Services.GetService(typeof(SpriteBatch));
                NombreBodego = nombreBodego;

               // movimiento = new Vector2(64.0f, 64.0f);
        }

        public override void Update(GameTime gameTime)
        {
            


           /*
            charx = textoArchivo[0];
            charx2 = textoArchivo[1];

            chary = textoArchivo[3];  
            
          
            if ((int)Char.GetNumericValue(charx) == 1)
                X = ((int)Char.GetNumericValue(charx2) + 10) * 64 - 64;
            else
                X = (int)Char.GetNumericValue(charx2) * 64 - 64;

            Y = traduceY[(int)Char.GetNumericValue(chary) - 1];
            */

            this.setPosition(new Vector2(X, Y));

            base.Update(gameTime);
        }

        public override void Draw(GameTime gameTime)
        {
            sBatch.Draw(texture, position, Color.White);
            base.Draw(gameTime);
        }

        public void setPosition(Vector2 pos) 
        {
            position = pos;
        }

        public Vector2 getPosition() 
        {
            return position;
        }

        public Texture2D getImage()
        {
            return texture;
        }  
    }
}
