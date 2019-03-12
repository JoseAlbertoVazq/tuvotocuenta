import { success, notFound } from '../../services/response/'
import { Partido } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Partido.create(body)
    .then((partido) => partido.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Partido.count(query)
    .then(count => Partido.find(query, select, cursor)
      .then((partidos) => ({
        count,
        rows: partidos.map((partido) => partido.view(true))
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Partido.findById(params.id)
    .then(notFound(res))
    .then((partido) => partido ? partido.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Partido.findById(params.id)
    .then(notFound(res))
    .then((partido) => partido ? Object.assign(partido, body).save() : null)
    .then((partido) => partido ? partido.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Partido.findById(params.id)
    .then(notFound(res))
    .then((partido) => partido ? partido.remove() : null)
    .then(success(res, 204))
    .catch(next)
