using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Llegada
    {
        protected Texture2D texture;
        public Texture2D caja;
        protected Vector2 position;
        public const int LLEGADAH = 192;
        public const int LLEGADAW = 64;

        public string paquete1, paquete2, paquete3;
        

        public Llegada(Texture2D theTexture, Texture2D caja)
        {
                texture = theTexture;
                this.caja = caja;
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
