import * as THREE from 'three';

let scene = new THREE.Scene();

let camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.set(0, 30, 90);
camera.lookAt(0, 0, 0);

let renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

let cupGeometry = createCupGeometry();
let cupMaterial = new THREE.MeshLambertMaterial({color: 0xFF3300});
let cupMesh = new THREE.Mesh(cupGeometry, cupMaterial);
cupMesh.castShadow = true;

const cupSpacing = 45;
const cupCount = 3;
let cups = [];
for (let i = 0; i < cupCount; i++) {
  let cup = cupMesh.clone();
  cup.translateX(cupSpacing * (i - ((cupCount - 1) / 2)));
  scene.add(cup);
  cups.push(cup);
}

let floorGeometry = new THREE.BoxGeometry(500, 5, 50);
let floorMaterial = new THREE.MeshLambertMaterial({color: 0x555555});
let floorMesh = new THREE.Mesh(floorGeometry, floorMaterial);
floorMesh.receiveShadow = true;
floorMesh.translateY(-((30 / 2) + (10 / 2)));
scene.add(floorMesh);

let light = new THREE.AmbientLight(0xFFFFFF, 0.1);
scene.add(light);

let light1 = new THREE.PointLight(0xFFFFFF, 0.6);
light1.position.set(0, -15, 50);
scene.add(light1);

let clock = new THREE.Clock();
let mixer = new THREE.AnimationMixer(scene);

for (const [i, cup] of cups.entries()) {
  let clip = createCupLiftAnimation(cup.position);
  let action = mixer.clipAction(clip, cup);
  action.setLoop(THREE.LoopOnce);
  action.startAt(i * 0.3).play();
}

let render = function () {
  requestAnimationFrame(render);

  let delta = clock.getDelta();
  mixer.update(delta);

  renderer.render(scene, camera);
};

render();

function createCupGeometry() {
  let sides = 50;
  let height = 30;
  let topRadius = 10;
  let bottomRadius = 15;

  // Compute vertex positions of unit circle
  let centralAngle = 2 * Math.PI / sides;
  let unitCircleVertices = [];

  for (let i = 0; i < sides; i++) {
    let vertex = new THREE.Vector3(
      Math.cos(centralAngle * i),
      Math.sin(centralAngle * i),
      0
    );
    unitCircleVertices.push(vertex);
  }

  // Hence compute the vertex positions of top and bottom of cup
  let topVertices = unitCircleVertices.map(v => v.clone().multiplyScalar(topRadius).add(new THREE.Vector3(0, 0, -height / 2)));
  let bottomVertices = unitCircleVertices.map(v => v.clone().multiplyScalar(bottomRadius).add(new THREE.Vector3(0, 0, height / 2)));

  // Add vertices to geometry
  let geometry = new THREE.Geometry();
  for (let i = 0; i < sides; i++) {

    let top = topVertices[i];
    let bottom = bottomVertices[i];

    geometry.vertices.push(top, bottom);
  }
  geometry.vertices.push(new THREE.Vector3(0, 0, -height / 2));

  // Create and add faces to geometry
  for (let i = 0; i < sides; i++) {
    let topPrevIndex = (i * 2) % (sides * 2);
    let bottomPrevIndex = ((i * 2) + 1) % (sides * 2);
    let topIndex = ((i * 2) + 2) % (sides * 2);
    let bottomIndex = ((i * 2) + 3) % (sides * 2);

    // Faces for side
    geometry.faces.push(
      new THREE.Face3(topPrevIndex, bottomIndex, bottomPrevIndex),
      new THREE.Face3(bottomIndex, topPrevIndex, topIndex)
    );

    // Face for top
    geometry.faces.push(
      new THREE.Face3(geometry.vertices.length - 1, topIndex, topPrevIndex)
    );

    // geometry.computeBoundingSphere();
    // geometry.computeFaceNormals();
    geometry.computeVertexNormals();
    geometry.verticesNeedUpdate = true;
    geometry.elementsNeedUpdate = true;
  }

  geometry.rotateX(Math.PI / 2);

  return geometry;
}

function createCupLiftAnimation(currentPosition) {
  let duration = 2;

  let name = '.position';
  let times = [0, duration / 2, duration];
  let values = [];
  currentPosition.toArray(values, values.length);
  currentPosition.clone().add(new THREE.Vector3(0, 20, 0)).toArray(values, values.length);
  currentPosition.toArray(values, values.length);

  let track = new THREE.VectorKeyframeTrack(name, times, values, THREE.InterpolateSmooth);

  let clip = new THREE.AnimationClip(null, duration, [track]);
  return clip;
}
