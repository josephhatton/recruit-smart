(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('ApplicantStatusSearch', ApplicantStatusSearch);

  ApplicantStatusSearch.$inject = ['$resource'];

  function ApplicantStatusSearch($resource) {
    var resourceUrl = 'api/_search/applicant-statuses/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
