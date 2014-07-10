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

import lombok.extern.log4j.Log4j;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @author Aliaksei Boole
 */
@Log4j
public class Sound
{
    private final static String SOUND_FILE = "beep.wav";
    private Clip clip;

    public Sound()
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(SOUND_FILE));
            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        }
        catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            log.error("Can't play music", e);
        }
    }

    public void beep()
    {
        clip.setFramePosition(0);
        clip.start();
    }
}
