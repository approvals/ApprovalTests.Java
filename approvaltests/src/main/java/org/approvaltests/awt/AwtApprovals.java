package org.approvaltests.awt;

import com.spun.util.images.ImageWriter;
import java.awt.Component;
import java.awt.Image;
import org.approvaltests.Approvals;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.writers.ComponentApprovalWriter;
import org.approvaltests.writers.ImageApprovalWriter;

import java.awt.image.BufferedImage;

public class AwtApprovals {
    public static void verify(Image image)
    {
        verifyBufferedImage(ImageWriter.toBufferedImage(image));
    }

    public static void verify(BufferedImage bufferedImage)
    {
        Approvals.verify(new ImageApprovalWriter(bufferedImage));
    }

    private static void verifyBufferedImage(BufferedImage bufferedImage)
    {
        Approvals.verify(new ImageApprovalWriter(bufferedImage));
    }

    public static void verify(Component c)
    {
        try (NamedEnvironment env = NamerFactory.asOsSpecificTest())
        {
            Approvals.verify(new ComponentApprovalWriter(c));
        }
    }
}
