package com.jaustinmiles.animationplayground;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class WorldManager {

    private final World world;
    private final float translate;

    @SuppressWarnings("unused")
    public WorldManager(float width, float height, float translate) {
        this.translate = translate;
        Vec2 g = new Vec2(0.0f, 20f);
        World world = new World(g);
        world.setWarmStarting(true);
        world.setContinuousPhysics(true);
        createCeiling(width, world);
        createGround(height, width, world);
        createWalls(height, width, world);
        this.world = world;
    }

    private void createCeiling(float width, World world) {
        BodyDef ceilingBodyDef = new BodyDef();
        ceilingBodyDef.position.set(coordPixelsToWorld(width/2, 0f + translate));
        Body ceilingBody = world.createBody(ceilingBodyDef);
        PolygonShape ceilingBox = new PolygonShape();
        Vec2 extents = coordPixelsToWorld(width/2, -1);
        ceilingBox.setAsBox(extents.x, extents.y);
        ceilingBody.createFixture(ceilingBox, 1f);
    }

    private void createGround(float height, float width, World world) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(coordPixelsToWorld(width/2, height));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        Vec2 extents = coordPixelsToWorld(width/2, -1);
        groundBox.setAsBox(extents.x, extents.y);
        groundBody.createFixture(groundBox, 1f);
    }

    private void createWalls(float height, float width, World world) {
        BodyDef wallBodyDef = new BodyDef();
        wallBodyDef.position.set(coordPixelsToWorld(0, height/2));
        Body wallBody = world.createBody(wallBodyDef);
        PolygonShape wallBox = new PolygonShape();
        Vec2 extents = coordPixelsToWorld(1, -height/2);
        wallBox.setAsBox(extents.x, extents.y);
        wallBody.createFixture(wallBox, 1f);

        BodyDef wallBodyDef2 = new BodyDef();
        wallBodyDef2.position.set(coordPixelsToWorld(width, height/2));
        Body wallBody2 = world.createBody(wallBodyDef2);
        PolygonShape wallBox2 = new PolygonShape();
        Vec2 extents2 = coordPixelsToWorld(1, -height/2);
        wallBox2.setAsBox(extents2.x, extents2.y);
        wallBody2.createFixture(wallBox2, 1f);
    }

    @SuppressWarnings("UnusedReturnValue")
    public World getWorld() {
        return this.world;
    }

    public void setGravity(float x, float y) {
        Vec2 g = new Vec2(x, y);
        world.setGravity(g);
    }

    public static Vec2 coordPixelsToWorld(float x, float y) {
        x = x / 20;
        y = y / 20;
        Vec2 vec = new Vec2();
        vec.set(x, -y);
        return vec;
    }

    public static float staticPixelsToWorld(float x) {
        return x / 20;
    }
}
