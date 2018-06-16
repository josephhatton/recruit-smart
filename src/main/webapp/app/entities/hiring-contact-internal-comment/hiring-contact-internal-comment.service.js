(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('HiringContactInternalComment', HiringContactInternalComment);

  HiringContactInternalComment.$inject = ['$resource'];

  function HiringContactInternalComment($resource) {
    var resourceUrl = 'api/hiring-contact-internal-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          if (data) {
            data = angular.fromJson(data);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
