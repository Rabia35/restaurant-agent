using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Piso
    {
        protected Texture2D texture;
        
        protected Vector2 position;
        public const int PISOWH = 64;
        

        public Piso(Texture2D theTexture)
        {
                texture = theTexture;          
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
