(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('ApplicantInternalComment', ApplicantInternalComment);

  ApplicantInternalComment.$inject = ['$resource'];

  function ApplicantInternalComment($resource) {
    var resourceUrl = 'api/applicant-internal-comments/:id';

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
