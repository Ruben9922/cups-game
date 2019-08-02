import * as BABYLON from 'babylonjs';

// Get the canvas DOM element
let canvas = document.getElementById('renderCanvas');

// Load the 3D engine
let engine = new BABYLON.Engine(canvas, true, {preserveDrawingBuffer: true, stencil: true});

// CreateScene function that creates and return the scene
let createScene = function () {
  // Create a basic BJS Scene object
  let scene = new BABYLON.Scene(engine);

  let camera = new BABYLON.ArcRotateCamera('camera1', 0, 0, 0, new BABYLON.Vector3.Zero(), scene);
  camera.setPosition(new BABYLON.Vector3(0, 30, 90));
  camera.fov = Math.PI / 3;

  // Create a basic light, aiming 0, 1, 0 - meaning, to the sky
  let light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 1, 0), scene);

  let latheMaterial = new BABYLON.StandardMaterial("latheMaterial", scene);
  latheMaterial.diffuseColor = new BABYLON.Color3(1, 0.2, 0);
  latheMaterial.specularColor = new BABYLON.Color3(1, 1, 1);

  var latheShape = [
    new BABYLON.Vector3(1.5, 0, 0),
    new BABYLON.Vector3(1, 3, 0),
  ];

  var lathe = BABYLON.MeshBuilder.CreateLathe('lathe', {shape: latheShape, cap: BABYLON.Mesh.CAP_END}, scene);
  lathe.material = latheMaterial;

  let ground = BABYLON.MeshBuilder.CreateGround('ground', {width: 15, height: 4.5}, scene);

  // Return the created scene
  return scene;
};

// call the createScene function
let scene = createScene();

// run the render loop
engine.runRenderLoop(function () {
  scene.render();
});

// the canvas/window resize event handler
window.addEventListener('resize', function () {
  engine.resize();
});
