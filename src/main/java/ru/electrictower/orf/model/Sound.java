/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.electrictower.orf.model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aliaksei Boole
 */
public class Sound
{
    private final static String SOUND_FILE = "beep.wav";
    private Clip clip;

    public Sound()
    {
        try
        {
            InputStream soundFile = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(SOUND_FILE);
            AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(soundFile));
            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        }
        catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            System.exit(-1);
        }
    }

    public void beep()
    {
        clip.setFramePosition(0);
        clip.start();
    }
}
