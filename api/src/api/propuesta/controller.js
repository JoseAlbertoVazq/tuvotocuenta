import { success, notFound, authorOrAdmin } from '../../services/response/'
import { Propuesta } from '.'
import { User } from '../user'
export const create = ({ user, bodymen: { body } }, res, next) =>
  Propuesta.create({ ...body, creador: user })
    .then((propuesta) => propuesta.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Propuesta.count(query)
    .then(count => Propuesta.find(query, select, cursor)
      .populate('creador').populate('partido', 'id siglas picture').populate('materia', 'id nombre')
      .then((propuestas) => ({
        count,
        rows: propuestas.map((propuesta) => propuesta.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Propuesta.findById(params.id)
    .populate('creador').populate('partido', 'picture').populate('materia', 'nombre')
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

export const addFavorite = ({ user, params }, res, next) =>
  User.findByIdAndUpdate(user.id, { $addToSet: { favs: params.id } }, { new: true })
    .then(success(res, 200))
    .catch(next)

export const delFavorite = ({ user, params }, res, next) =>
  User.findByIdAndUpdate(user.id, { $pull: { favs: params.id } }, { new: true })
    .then(success(res, 200))
    .catch(next)

export const userFavorites = ({ user, querymen: { query, select, cursor } }, res, next) => {
  query['_id'] = { $in: user.favs }
  Propuesta
    .find(query, select, cursor)
    .populate('creador')
    .populate('partido', 'id siglas picture')
    .populate('materia', 'id nombre')
    .then((result) => ({
      count: result.length,
      rows: result
    }))
    .then(success(res))
    .catch(next)
}

export const userPropuestas = ({ user, querymen: { query, select, cursor } }, res, next) => {
  query['creador'] = user.id
  Propuesta
    .find(query, select, cursor)
    .populate('creador', 'id, name')
    .populate('partido', 'id siglas picture')
    .populate('materia', 'id nombre')

    .then((result) => ({
      count: result.length,
      rows: result
    }))
    .then(success(res))
    .catch(next)
}