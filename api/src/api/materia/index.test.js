import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Materia } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, materia

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  materia = await Materia.create({})
})

test('POST /materias 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, nombre: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.nombre).toEqual('test')
})

test('POST /materias 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /materias 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /materias 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /materias 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /materias/:id 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${materia.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(materia.id)
})

test('GET /materias/:id 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}/${materia.id}`)
  expect(status).toBe(401)
})

test('GET /materias/:id 404 (user)', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
    .query({ access_token: userSession })
  expect(status).toBe(404)
})

test('PUT /materias/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${materia.id}`)
    .send({ access_token: adminSession, nombre: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(materia.id)
  expect(body.nombre).toEqual('test')
})

test('PUT /materias/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${materia.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /materias/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${materia.id}`)
  expect(status).toBe(401)
})

test('PUT /materias/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, nombre: 'test' })
  expect(status).toBe(404)
})

test('DELETE /materias/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${materia.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /materias/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${materia.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /materias/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${materia.id}`)
  expect(status).toBe(401)
})

test('DELETE /materias/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
