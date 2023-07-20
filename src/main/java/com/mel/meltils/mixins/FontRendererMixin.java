package com.mel.meltils.mixins;

import com.mel.meltils.shader.Shader;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(FontRenderer.class)
public abstract class FontRendererMixin {
    @Shadow protected abstract void setColor(float r, float g, float b, float a);

    @Shadow private int textColor;

    @Shadow private float alpha;

    @Shadow private int[] colorCode;

    @ModifyConstant(method = "renderStringAtPos", constant = @Constant(stringValue = "0123456789abcdefklmnor"))
    public String addColorCode(String string) {
        return "0123456789abcdefklmnorz";
    }

    private boolean shadowEnabled = false;

    @Inject(method = "renderStringAtPos", at = @At(value = "HEAD"))
    public void setShadow(String text, boolean shadow, CallbackInfo ci) {
        shadowEnabled = shadow;
    }

    @Redirect(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Ljava/lang/String;indexOf(I)I", ordinal = 0))
    public int applyColor(String instance, int i) {
        int i1 = instance.indexOf(i);
        Shader shader = Shader.INSTANCE;
        if (i1 == 22) {
            //start color
            if (shadowEnabled) {
                this.textColor = this.colorCode[24];
                setColor((float)(this.textColor >> 16) / 255.0F, (float)(this.textColor >> 8 & 255) / 255.0F, (float)(this.textColor & 255) / 255.0F, this.alpha);
            } else {
                Color color = Color.magenta;
                this.textColor = color.getRGB();
                setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, this.alpha);
                shader.bind();
            }
        } else if (i1 == 21) {
            //end color
            shader.unbind();
            setColor(1f, 1f, 1f, 1f);
        }
        return i1;
    }
}
