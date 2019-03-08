import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Partido } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, partido

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  partido = await Partido.create({})
})

test('POST /partidos 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, nombre: 'test', siglas: 'test', propuestas: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.nombre).toEqual('test')
  expect(body.siglas).toEqual('test')
  expect(body.propuestas).toEqual('test')
})

test('POST /partidos 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /partidos 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /partidos 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /partidos 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /partidos/:id 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${partido.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(partido.id)
})

test('GET /partidos/:id 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}/${partido.id}`)
  expect(status).toBe(401)
})

test('GET /partidos/:id 404 (user)', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
    .query({ access_token: userSession })
  expect(status).toBe(404)
})

test('PUT /partidos/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${partido.id}`)
    .send({ access_token: adminSession, nombre: 'test', siglas: 'test', propuestas: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(partido.id)
  expect(body.nombre).toEqual('test')
  expect(body.siglas).toEqual('test')
  expect(body.propuestas).toEqual('test')
})

test('PUT /partidos/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${partido.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /partidos/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${partido.id}`)
  expect(status).toBe(401)
})

test('PUT /partidos/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, nombre: 'test', siglas: 'test', propuestas: 'test' })
  expect(status).toBe(404)
})

test('DELETE /partidos/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${partido.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /partidos/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${partido.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /partidos/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${partido.id}`)
  expect(status).toBe(401)
})

test('DELETE /partidos/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
