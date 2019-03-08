import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Propuesta } from '.'

const app = () => express(apiRoot, routes)

let userSession, anotherSession, propuesta

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const anotherUser = await User.create({ email: 'b@b.com', password: '123456' })
  userSession = signSync(user.id)
  anotherSession = signSync(anotherUser.id)
  propuesta = await Propuesta.create({ creador: user })
})

test('POST /propuestas 201 (user)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession, titulo: 'test', contenido: 'test', materia: 'test', partido: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.titulo).toEqual('test')
  expect(body.contenido).toEqual('test')
  expect(body.materia).toEqual('test')
  expect(body.partido).toEqual('test')
  expect(typeof body.creador).toEqual('object')
})

test('POST /propuestas 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /propuestas 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
  expect(typeof body.rows[0].creador).toEqual('object')
})

test('GET /propuestas 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /propuestas/:id 200 (user)', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${propuesta.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(propuesta.id)
  expect(typeof body.creador).toEqual('object')
})

test('GET /propuestas/:id 401', async () => {
  const { status } = await request(app())
    .get(`${apiRoot}/${propuesta.id}`)
  expect(status).toBe(401)
})

test('GET /propuestas/:id 404 (user)', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
    .query({ access_token: userSession })
  expect(status).toBe(404)
})

test('PUT /propuestas/:id 200 (user)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${propuesta.id}`)
    .send({ access_token: userSession, titulo: 'test', contenido: 'test', materia: 'test', partido: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(propuesta.id)
  expect(body.titulo).toEqual('test')
  expect(body.contenido).toEqual('test')
  expect(body.materia).toEqual('test')
  expect(body.partido).toEqual('test')
  expect(typeof body.creador).toEqual('object')
})

test('PUT /propuestas/:id 401 (user) - another user', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${propuesta.id}`)
    .send({ access_token: anotherSession, titulo: 'test', contenido: 'test', materia: 'test', partido: 'test' })
  expect(status).toBe(401)
})

test('PUT /propuestas/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${propuesta.id}`)
  expect(status).toBe(401)
})

test('PUT /propuestas/:id 404 (user)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: anotherSession, titulo: 'test', contenido: 'test', materia: 'test', partido: 'test' })
  expect(status).toBe(404)
})

test('DELETE /propuestas/:id 204 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${propuesta.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(204)
})

test('DELETE /propuestas/:id 401 (user) - another user', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${propuesta.id}`)
    .send({ access_token: anotherSession })
  expect(status).toBe(401)
})

test('DELETE /propuestas/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${propuesta.id}`)
  expect(status).toBe(401)
})

test('DELETE /propuestas/:id 404 (user)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: anotherSession })
  expect(status).toBe(404)
})
