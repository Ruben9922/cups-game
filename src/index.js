import * as THREE from 'three';

var scene = new THREE.Scene();

var camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.z = 100;

var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

var geometry = createCupGeometry();
var material = new THREE.MeshLambertMaterial({color: 0xfd59d7});
var cup = new THREE.Mesh(geometry, material);
scene.add(cup);

var light = new THREE.PointLight(0xFFFF00);
light.position.set(10, 0, 25);
scene.add(light);

var render = function () {
  requestAnimationFrame(render);
  cup.rotation.x += 0.01;
  geometry.computeVertexNormals();

  renderer.render(scene, camera);
};

render();

function createCupGeometry() {
  let sides = 20;
  let height = 20;
  let topRadius = 10;
  let bottomRadius = 20;

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
  // Create and add faces to geometry
  let geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(0, 0, -height / 2));
  for (let i = 0; i < sides; i++) {
    let topLeft = topVertices[(i + 1) % sides];
    let topRight = topVertices[i];
    let bottomLeft = bottomVertices[(i + 1) % sides];
    let bottomRight = bottomVertices[i];

    geometry.vertices.push(topLeft, topRight, bottomLeft, bottomRight);

    let topLeftIndex = geometry.vertices.length - 4;
    let topRightIndex = geometry.vertices.length - 3;
    let bottomLeftIndex = geometry.vertices.length - 2;
    let bottomRightIndex = geometry.vertices.length - 1;

    // Faces for side
    geometry.faces.push(
      new THREE.Face3(topLeftIndex, bottomLeftIndex, topRightIndex),
      new THREE.Face3(bottomRightIndex, topRightIndex, bottomLeftIndex)
    );

    // Face for top
    geometry.faces.push(
      new THREE.Face3(0, topLeftIndex, topRightIndex)
    );
    geometry.computeBoundingSphere();
    // geometry.computeFaceNormals();
    // geometry.computeVertexNormals();
    geometry.verticesNeedUpdate = true;
    geometry.elementsNeedUpdate = true;
  }

  console.log(geometry);

  return geometry;
}
