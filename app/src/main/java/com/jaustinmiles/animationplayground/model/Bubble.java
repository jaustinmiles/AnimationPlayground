package com.jaustinmiles.animationplayground.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.jaustinmiles.animationplayground.MainActivity;
import com.jaustinmiles.animationplayground.WorldManager;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Bubble extends android.support.v7.widget.AppCompatTextView {

    private static final float BUBBLE_RESTITUTION = 0.4f;
    private static final float BUBBLE_DENSITY = 1.0f;
    private static final float BUBBLE_FRICTION = 0.3f;

    private Body body;
    private World world;
    private ImageView imageView;
    private int bubbleRadius;
    private Long taskId;

    public Bubble(Context context) {
        super(context);
    }

    public Bubble(Context context, World world, float x, float y, int radius) {
        super(context);
        this.bubbleRadius = radius;
        Body body = getBody(world, x, y);
        CircleShape bubbleShape = getCircleShape();
        setFixture(body, bubbleShape);
        this.imageView = new ImageView(context);
        this.body = body;
        this.world = world;
    }

    private void setFixture(Body body, CircleShape bubbleShape) {
        FixtureDef bubbleFixture = new FixtureDef();
        bubbleFixture.shape = bubbleShape;
        bubbleFixture.density = BUBBLE_DENSITY;
        bubbleFixture.friction = BUBBLE_FRICTION;
        bubbleFixture.restitution = BUBBLE_RESTITUTION;
        body.createFixture(bubbleFixture);
    }

    @NotNull
    private CircleShape getCircleShape() {
        CircleShape bubbleShape = new CircleShape();
        bubbleShape.setRadius(WorldManager.staticPixelsToWorld(this.bubbleRadius));
        return bubbleShape;
    }

    private Body getBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(WorldManager.coordPixelsToWorld(x, y));
        return world.createBody(bodyDef);
    }

    public void setBmp(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
        this.setBackgroundDrawable(imageView.getDrawable());
    }

    public void rotate() {
        float rotation = body.getAngle();
        Matrix matrix = new Matrix();
        matrix.postRotate((float) (rotation / Math.PI * 180), imageView.getDrawable().getBounds().width() >> 1,
                imageView.getDrawable().getBounds().height() >> 1);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageMatrix(matrix);
    }

    @SuppressWarnings("UnusedReturnValue")
    public float getWorldX() {
        return body.getPosition().x * 20 - this.bubbleRadius;
    }

    @SuppressWarnings("UnusedReturnValue")
    public float getWorldY() {
        return -body.getPosition().y * 20 - this.bubbleRadius;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return super.onTouchEvent(event);
}

    @Override
    public boolean performClick() {
        if (((MainActivity) getContext()).isPoppable()) {
            ((CoordinatorLayout) getParent()).removeView(this);
            ArrayList<Bubble> bubbles = ((MainActivity) getContext()).getBubbles();
            try {
                ((MainActivity) getContext()).deleteTaskFromDB(this.taskId);
            } catch (NullPointerException e) {
                System.out.println("Task is not in database. Ensure this was used for testing purposes.");
            }


            bubbles.remove(this);
            world.destroyBody(this.body);
        }
        return super.performClick();
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
