import { success, notFound, authorOrAdmin } from '../../services/response/'
import { Propuesta } from '.'

export const create = ({ user, bodymen: { body } }, res, next) =>
  Propuesta.create({ ...body, creador: user })
    .then((propuesta) => propuesta.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Propuesta.count(query)
    .then(count => Propuesta.find(query, select, cursor)
      .populate('creador')
      .then((propuestas) => ({
        count,
        rows: propuestas.map((propuesta) => propuesta.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Propuesta.findById(params.id)
    .populate('creador')
    .then(notFound(res))
    .then((propuesta) => propuesta ? propuesta.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ user, bodymen: { body }, params }, res, next) =>
  Propuesta.findById(params.id)
    .populate('creador')
    .then(notFound(res))
    .then(authorOrAdmin(res, user, 'creador'))
    .then((propuesta) => propuesta ? Object.assign(propuesta, body).save() : null)
    .then((propuesta) => propuesta ? propuesta.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ user, params }, res, next) =>
  Propuesta.findById(params.id)
    .then(notFound(res))
    .then(authorOrAdmin(res, user, 'creador'))
    .then((propuesta) => propuesta ? propuesta.remove() : null)
    .then(success(res, 204))
    .catch(next)
